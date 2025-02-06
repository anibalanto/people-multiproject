package ar.lamansys.education.educationclass.infrastructure.rest.input;

import ar.lamansys.education.educationclass.application.student.GetStudentsByClass;
import ar.lamansys.education.educationclass.application.teacher.GetTeachersByClass;
import ar.lamansys.people.shared.object.PersonSO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/class/{className}")
@RequiredArgsConstructor
public class ClassPeopleController {
    private final GetStudentsByClass getStudentsByClass;

    private final GetTeachersByClass getTeachersByClass;

    @GetMapping("/students")
    public List<PersonSO> getStudents(
            @PathVariable("className") String className) {
        return getStudentsByClass.run(className);
    }

    @GetMapping("/teachers")
    public List<PersonSO> getTeachers(
            @PathVariable("className") String className) {
        return getTeachersByClass.run(className);
    }

}
