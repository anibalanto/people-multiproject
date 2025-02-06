package ar.lamansys.healt.consultory.application.doctor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetDoctorsUrisByConsultory {
    List<String> run(String consultoryName) {
        return List.of(
                "uriDoctor1",
                "uriDoctor2"
        );
    }
}
