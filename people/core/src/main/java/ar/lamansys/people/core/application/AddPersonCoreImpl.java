package ar.lamansys.people.core.application;

import ar.lamansys.people.core.domain.PersonDV;
import ar.lamansys.people.shared.application.AddPerson;
import ar.lamansys.people.shared.object.PersonSO;
import ar.sharepath.dynamicvalidation.core.ValidatorRegistry;
import org.springframework.stereotype.Service;

@Service
public class AddPersonCoreImpl implements AddPerson {

    static {
        PersonDV.initialize();
    }

    @Override
    public void run(PersonSO person) {
        ValidatorRegistry
                .getValidator(PersonSO.class)
                .validate(person);

        person.setEmail("juanArrobajuan.com");
        ValidatorRegistry
                .getValidator(PersonSO.class)
                .validate(person);

    }

}
