package ar.lamansys.education.educationclass.application.student;

import ar.lamansys.people.shared.application.AddPerson;
import ar.lamansys.people.shared.object.PersonSO;

public class AddStudentByClass {
    AddPerson addPerson;

    void run() {
        var student = new PersonSO(
                "uri",
                "Juan uri",
                "Rodriguez de uri",
                "juan@juan.com",
                22
        );
        addPerson.run(student);
    }
}
