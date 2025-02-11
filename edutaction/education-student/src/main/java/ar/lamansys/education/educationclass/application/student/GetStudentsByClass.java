package ar.lamansys.education.educationclass.application.student;

import ar.lamansys.people.shared.application.GetPersonByUri;
import ar.lamansys.people.shared.object.PersonSO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetStudentsByClass {

        private final GetStudentsUrisByClass getPeopleUrisByClass;
        private final GetPersonByUri getPersonByUri;

        public List<PersonSO> run(String className) {
            return getPeopleUrisByClass.run(className).stream()
                    .map(
                        getPersonByUri::run
                    )
                    .collect(Collectors.toList());
        }

}
