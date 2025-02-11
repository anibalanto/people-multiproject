package ar.sharepath.dynamicvalidation.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FieldValidator<T> {

    private final String fieldName;
    private boolean checked = false;
    private final List<Consumer<T>> validators = new ArrayList<>();

    public FieldValidator(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void addValidator(Consumer<T> validator) {
        validators.add(validator);
    }

    /**
     * Ejecuta todos los validadores sobre el valor.
     * Cada Consumer debe lanzar una excepción en caso de fallo.
     */
    public void validate(T value) {
        if( this.checked ) return;

        for (Consumer<T> validator : validators) {
            validator.accept(value);
        }

        this.checked = true;
    }
}
