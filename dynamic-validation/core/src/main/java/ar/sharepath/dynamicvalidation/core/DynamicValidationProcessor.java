package ar.sharepath.dynamicvalidation.core;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("ar.sharepath.dynamicvalidation.core.DynamicValidation")
@SupportedSourceVersion(SourceVersion.RELEASE_22)
@AutoService(Processor.class)
public class DynamicValidationProcessor extends AbstractProcessor {

    private Elements elementUtils;

    public DynamicValidationProcessor() {
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // Procesa cada clase anotada con @DynamicValidation
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(DynamicValidation.class)) {
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                continue;
            }
            TypeElement classElement = (TypeElement) annotatedElement;
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    "Procesando clase: " + classElement.getQualifiedName());

            // Llamamos al método que genera el validador extraendo los fields y sus tipos
            generateValidator(classElement, processingEnv.getFiler(), elementUtils);
        }
        return true;
    }

    /**
     * Genera una clase Validator para la clase original (por ejemplo, PersonSOValidator) a partir de los campos
     * encontrados en la clase anotada.
     *
     * Se genera un código similar al siguiente:
     *
     * <pre>
     * package ar.lamansys.people.shared.object;
     *
     * import ar.sharepath.dynamicvalidation.core.FieldValidator;
     * import ar.sharepath.dynamicvalidation.core.Validator;
     * import ar.sharepath.dynamicvalidation.core.ValidatorRegistry;
     * import java.util.function.Consumer;
     *
     * public class PersonSOValidator {
     *   private static final Validator&lt;PersonSO&gt; validator = buildValidator();
     *
     *   static {
     *       ValidatorRegistry.registerValidator(PersonSO.class, validator);
     *   }
     *
     *   private static Validator&lt;PersonSO&gt; buildValidator() {
     *       Validator&lt;PersonSO&gt; validator = new Validator&lt;PersonSO&gt;();
     *       validator.addFieldValidator("uri", new FieldValidator&lt;String&gt;("uri"));
     *       // ... (para cada campo)
     *       return validator;
     *   }
     *
     *   public static PersonSOValidator validations() {
     *       return new PersonSOValidator();
     *   }
     *
     *   public void register() {
     *       ValidatorRegistry.registerValidator(PersonSO.class, this.validator);
     *   }
     *
     *   public PersonSOValidator uri(Consumer&lt;String&gt;... validators) {
     *       for (Consumer&lt;String&gt; validator : validators) {
     *           Validator&lt;PersonSO&gt; myValidator = ValidatorRegistry.getValidator(PersonSO.class);
     *           myValidator.<String>getFieldValidator("uri").addValidator(validator);
     *       }
     *       return this;
     *   }
     *
     *   // Métodos para los demás campos (name, lastName, email, age, etc.)
     * }
     * </pre>
     *
     * @param classElement la clase anotada
     * @param filer el Filer para escribir los archivos generados
     * @param elementUtils utilidades de elementos
     */
    public void generateValidator(TypeElement classElement, Filer filer, Elements elementUtils) {
        // Nombre y paquete de la clase original
        String originalClassName = classElement.getSimpleName().toString(); // Por ejemplo, "PersonSO"
        String packageName = elementUtils.getPackageOf(classElement).getQualifiedName().toString();
        String generatedClassName = originalClassName + "Validator";

        // Referencia a la clase original
        ClassName originalClassType = ClassName.get(packageName, originalClassName);

        // Clases de apoyo (estas deben existir en tu módulo de validación)
        ClassName validatorClass = ClassName.get("ar.sharepath.dynamicvalidation.core", "Validator");
        ClassName fieldValidatorClass = ClassName.get("ar.sharepath.dynamicvalidation.core", "FieldValidator");
        ClassName validatorRegistry = ClassName.get("ar.sharepath.dynamicvalidation.core", "ValidatorRegistry");
        ClassName consumerClass = ClassName.get("java.util.function", "Consumer");

        // Tipo: Validator<OriginalClass>
        ParameterizedTypeName validatorOfOriginal = ParameterizedTypeName.get(validatorClass, originalClassType);

        // Campo: private static final Validator<OriginalClass> validator = buildValidator();
        FieldSpec validatorField = FieldSpec.builder(validatorOfOriginal, "validator",
                        Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("buildValidator()")
                .build();

        // Bloque static para registrar el validator en ValidatorRegistry
        CodeBlock staticBlock = CodeBlock.builder()
                .addStatement("$T.registerValidator($T.class, validator)", validatorRegistry, originalClassType)
                .build();

        // Método buildValidator(): privado y estático
        MethodSpec.Builder buildValidatorBuilder = MethodSpec.methodBuilder("buildValidator")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .returns(validatorOfOriginal)
                .addStatement("$T validator = new $T()", validatorOfOriginal, validatorOfOriginal);

        // Recolecta todos los campos (variables) de la clase original
        List<? extends Element> enclosedElements = classElement.getEnclosedElements();
        List<VariableElement> fields = new ArrayList<>();
        for (Element e : enclosedElements) {
            if (e.getKind() == ElementKind.FIELD) {
                fields.add((VariableElement) e);
            }
        }
        // Para cada campo, se agrega una instrucción que añade un FieldValidator con el nombre y tipo adecuado
        for (VariableElement field : fields) {
            String fieldName = field.getSimpleName().toString();
            TypeName fieldType = TypeName.get(field.asType());
            buildValidatorBuilder.addStatement("validator.addFieldValidator($S, new $T($S))",
                    fieldName,
                    ParameterizedTypeName.get(fieldValidatorClass, fieldType),
                    fieldName);
        }
        buildValidatorBuilder.addStatement("return validator");
        MethodSpec buildValidatorMethod = buildValidatorBuilder.build();

        // Método validations(): static que retorna una nueva instancia del validador generado
        MethodSpec validationsMethod = MethodSpec.methodBuilder("validations")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get(packageName, generatedClassName))
                .addStatement("return new $N()", generatedClassName)
                .build();

        // Método register(): instancia que registra el validator en el ValidatorRegistry
        MethodSpec registerMethod = MethodSpec.methodBuilder("register")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addStatement("$T.registerValidator($T.class, this.validator)", validatorRegistry, originalClassType)
                .build();

        // Creamos el TypeSpec.Builder para la clase generada
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(generatedClassName)
                .addModifiers(Modifier.PUBLIC)
                .addField(validatorField)
                .addStaticBlock(staticBlock)
                .addMethod(buildValidatorMethod)
                .addMethod(validationsMethod)
                .addMethod(registerMethod);

        // Para cada campo, generamos un método que reciba un varargs de Consumer<FieldType>
        // y asocie cada uno de ellos al FieldValidator correspondiente.
        for (VariableElement field : fields) {
            String fieldName = field.getSimpleName().toString();
            TypeName fieldType = TypeName.get(field.asType());
            MethodSpec fieldMethod = MethodSpec.methodBuilder(fieldName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(ClassName.get(packageName, generatedClassName))
                    .varargs(true)
                    .addParameter(ArrayTypeName.of(ParameterizedTypeName.get(consumerClass, fieldType)), "validators")
                    .beginControlFlow("for ($T validator : validators)", ParameterizedTypeName.get(consumerClass, fieldType))
                    .addStatement("$T myValidator = $T.getValidator($T.class)", validatorOfOriginal, validatorRegistry, originalClassType)
                    .addStatement("myValidator.<$T>getFieldValidator($S).addValidator(validator)", fieldType, fieldName)
                    .endControlFlow()
                    .addStatement("return this")
                    .build();
            classBuilder.addMethod(fieldMethod);
        }

        // Construye la clase final
        TypeSpec validatorGeneratedClass = classBuilder.build();
        JavaFile javaFile = JavaFile.builder(packageName, validatorGeneratedClass)
                .build();

        // Escribe el archivo generado usando el Filer del procesador
        try {
            javaFile.writeTo(filer);
        } catch (javax.annotation.processing.FilerException fe) {
            // El archivo ya existe; no se vuelve a generar.
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.ERROR,
                    "Error generando el validator para " + originalClassName + ": " + e.getMessage()
            );
        }
    }
}
