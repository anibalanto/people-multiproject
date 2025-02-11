package ar.lamansys.education.educationclass.application.student;

import ar.lamansys.people.shared.application.AddPerson;
import ar.lamansys.people.shared.object.PersonSO;
import ar.sharepath.dynamicvalidation.core.ValidatorRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddStudentByClass {
    private final AddPerson addPerson;

    public void run(String className) {
        var person = new PersonSO(
                "uri",
                "Juan uri",
                "Rodriguez de uri",
                "juan@juan.com",
                22
        );
        addPerson.run(person);
    }
}
