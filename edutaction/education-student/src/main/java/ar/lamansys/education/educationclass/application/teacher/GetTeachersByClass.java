package ar.lamansys.education.educationclass.application.teacher;

import ar.lamansys.people.shared.application.GetPersonByUri;
import ar.lamansys.people.shared.object.PersonSO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetTeachersByClass {

        private final GetTeachersUrisByClass getTeachersUrisByClass;
        private final GetPersonByUri getPersonByUri;

        public List<PersonSO> run(String className) {
            return getTeachersUrisByClass.run(className).stream()
                    .map(
                        getPersonByUri::run
                    )
                    .collect(Collectors.toList());
        }

}
