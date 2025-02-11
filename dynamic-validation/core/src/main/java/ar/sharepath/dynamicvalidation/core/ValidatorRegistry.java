package ar.sharepath.dynamicvalidation.core;

import java.util.HashMap;
import java.util.Map;

public class ValidatorRegistry {

    // Registro global: clave = Type de la clase, valor = Validator de la clase
    private static final Map<Class<?>, Validator<?>> registry = new HashMap<>();

    public static <T> void registerValidator(Class<T> clazz, Validator<T> validator) {
        registry.put(clazz, validator);
    }

    public static <T> Validator<T> getValidator(Class<T> clazz) {
        return (Validator<T>) registry.get(clazz);
    }
}
