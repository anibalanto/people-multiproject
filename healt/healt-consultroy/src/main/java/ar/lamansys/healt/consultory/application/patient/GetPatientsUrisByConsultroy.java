package ar.lamansys.healt.consultory.application.patient;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPatientsUrisByConsultroy {
    List<String> run(String consultoryName) {
        return List.of(
                "uriPatient1",
                "uriPAtient2",
                "uriPatient3"
        );
    }
}
