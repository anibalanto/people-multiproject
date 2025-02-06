package ar.lamansys.healt.consultory.application.patient;

import ar.lamansys.people.shared.application.GetPersonByUri;
import ar.lamansys.people.shared.object.PersonSO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetPatientsByConsultory {

        private final GetPatientsUrisByConsultroy getPeopleUrisByClass;
        private final GetPersonByUri getPersonByUri;

        public List<PersonSO> run(String consultroyName) {
            return getPeopleUrisByClass.run(consultroyName).stream()
                    .map(
                        getPersonByUri::run
                    )
                    .collect(Collectors.toList());
        }

}
