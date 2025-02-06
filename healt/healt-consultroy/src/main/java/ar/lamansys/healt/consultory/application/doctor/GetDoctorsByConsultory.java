package ar.lamansys.healt.consultory.application.doctor;

import ar.lamansys.people.shared.application.GetPersonByUri;
import ar.lamansys.people.shared.object.PersonSO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetDoctorsByConsultory {

        private final GetDoctorsUrisByConsultory getDoctorsUrisByConsultory;
        private final GetPersonByUri getPersonByUri;

        public List<PersonSO> run(String consultoryName) {
            return getDoctorsUrisByConsultory.run(consultoryName).stream()
                    .map(
                        getPersonByUri::run
                    )
                    .collect(Collectors.toList());
        }

}
