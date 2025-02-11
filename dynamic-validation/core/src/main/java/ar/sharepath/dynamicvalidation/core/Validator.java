package ar.sharepath.dynamicvalidation.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Validator<T> {

    // Asocia el nombre del campo con su FieldValidator
    private final Map<String, FieldValidator<?>> fieldValidators = new HashMap<>();

    public void addFieldValidator(String fieldName, FieldValidator<?> validator) {
        fieldValidators.put(fieldName, validator);
    }

    @SuppressWarnings("unchecked")
    public <F> FieldValidator<F> getFieldValidator(String fieldName) {
        return (FieldValidator<F>) fieldValidators.get(fieldName);
    }

    /**
     * Valida la instancia T. Se recorre cada FieldValidator y se llama a validate() con el valor obtenido.
     * Se asume que el objeto tiene getters que siguen la convención "get<FieldName>".
     */
    public void validate(T instance) {

        Function<String, String>  capitalize = (String str) -> {
            if(str == null || str.isEmpty()){
                return str;
            }
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        };


        // La lógica para obtener los valores de cada campo (por ejemplo, por reflexión)
        // puede implementarse aquí o generarse código específico para cada clase.
        // Se deja como ejemplo:
        fieldValidators.forEach((fieldName, fieldValidator) -> {
            try {
                // Suponiendo que existe un getter get<FieldName>
                Object value = instance.getClass()
                        .getMethod("get" + capitalize.apply(fieldName))
                        .invoke(instance);
                // Ejecuta la validación.
                ((FieldValidator<Object>) fieldValidator).validate(value);
            } catch (Exception e) {
                throw new RuntimeException("Error al validar el campo: " + fieldName, e);
            }
        });
    }
}
