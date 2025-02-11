package ar.lamansys.people.core.domain;

import ar.lamansys.people.shared.object.PersonSOValidator;

//Person Domain Validations
public class PersonDV {

    public static void initialize() {
        PersonSOValidator
                .validations()
                .email(email -> {
                    if (email == null || email.isEmpty()) {
                        throw new IllegalArgumentException("Email is required");
                    }
                    if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                        throw new IllegalArgumentException("Email is invalid");
                    }
                    if (email.length() > 100) {
                        throw new IllegalArgumentException("Email is too long");
                    }
                })
                .name(name -> {
                    if (name == null || name.isEmpty()) {
                        throw new IllegalArgumentException("Name is required");
                    }
                })
                .lastName(lastName -> {
                    if (lastName == null || lastName.isEmpty()) {
                        throw new IllegalArgumentException("Last name is required");
                    }
                })
                .age(age -> {
                    if (age == null) {
                        throw new IllegalArgumentException("Age is required");
                    }
                    if (age < 0) {
                        throw new IllegalArgumentException("Age is invalid");
                    }
                })
                .register();
    }
}
