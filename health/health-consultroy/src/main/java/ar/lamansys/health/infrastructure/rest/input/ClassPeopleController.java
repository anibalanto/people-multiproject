package ar.lamansys.health.infrastructure.rest.input;

import ar.lamansys.health.application.patient.GetPatientsByConsultory;
import ar.lamansys.health.application.doctor.GetDoctorsByConsultory;
import ar.lamansys.people.shared.object.PersonSO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consultory/{consultoryName}")
@RequiredArgsConstructor
public class ClassPeopleController {
    private final GetPatientsByConsultory getPatientsByConsultory;

    private final GetDoctorsByConsultory getTeachersByConsultory;

    @GetMapping("/patients")
    public List<PersonSO> getStudents(
            @PathVariable("consultoryName") String consultoryName) {
        return getPatientsByConsultory.run(consultoryName);
    }

    @GetMapping("/doctors")
    public List<PersonSO> getTeachers(
            @PathVariable("consultoryName") String consultoryName) {
        return getTeachersByConsultory.run(consultoryName);
    }

}
