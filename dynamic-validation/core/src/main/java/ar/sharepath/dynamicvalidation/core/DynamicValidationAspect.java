package ar.sharepath.dynamicvalidation.core;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class DynamicValidationAspect {

    private static final Logger log = LoggerFactory.getLogger(DynamicValidationAspect.class);

    // Captura todos los métodos en clases anotadas con @DynamicValidation
    @Pointcut("within(@ar.sharepath.dynamicvalidation.core.DynamicValidation *)")
    public void dynamicValidationClasses() {}

    // Por ejemplo, para interceptar setters públicos:
    @Around("execution(public void set*(..)) && dynamicValidationClasses()")
    public Object interceptSetter(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String fieldName = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
        log.info("Intercepted method: {} for field: {}", methodName, fieldName);
        System.out.println("Intercepted method: " + methodName + " for field: " + fieldName);

        Object newValue = joinPoint.getArgs()[0];
        Object target = joinPoint.getTarget();

        Validator validator = ValidatorRegistry.getValidator(target.getClass());
        if (validator != null) {
            FieldValidator fieldValidator = validator.getFieldValidator(fieldName);
            if (fieldValidator != null) {
                fieldValidator.setChecked(false);
            }
        }
        return joinPoint.proceed();
    }
}
