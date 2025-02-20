package ar.lamansys.education.educationclass.infrastructure.rest.input;

import ar.lamansys.alert.shared.application.GetAlertByUri;
import ar.lamansys.alert.shared.object.AlertSO;
import ar.lamansys.education.educationclass.application.student.AddStudentByClass;
import ar.lamansys.education.educationclass.application.student.GetStudentsByClass;
import ar.lamansys.education.educationclass.application.teacher.GetTeachersByClass;
import ar.lamansys.people.shared.object.PersonSO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/class/{className}")
@RequiredArgsConstructor
public class ClassPeopleController {
    private final GetStudentsByClass getStudentsByClass;

    private final GetTeachersByClass getTeachersByClass;

    private final AddStudentByClass addStudentByClass;

    private final GetAlertByUri getAlertByUri;

    @GetMapping("/students/{uri}/alerts")
    public List<AlertSO> getStudentAlerts(
            @PathVariable("className") String className,
            @PathVariable("uri") String uri) {
        return getAlertByUri.run(uri);
    }

    @PostMapping("/students")
    public void postStudent(
            @PathVariable("className") String className) {
        addStudentByClass.run(className);
    }

    @GetMapping("/teachers")
    public List<PersonSO> getTeachers(
            @PathVariable("className") String className) {
        return getTeachersByClass.run(className);
    }

}
