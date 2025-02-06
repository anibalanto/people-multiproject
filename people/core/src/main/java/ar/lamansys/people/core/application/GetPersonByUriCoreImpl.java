package ar.lamansys.people.core.application;

import ar.lamansys.people.shared.application.GetPersonByUri;
import ar.lamansys.people.shared.object.PersonSO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class GetPersonByUriCoreImpl implements GetPersonByUri {
    @Override
    public PersonSO run(String uri) {
        log.info("Consultando persona en Core: {}", uri);
        return new PersonSO(
                uri,
                String.format("Juan %s", uri),
                String.format("Rodriguez de %s", uri),
                String.format("juan_%s_rodriguez_de_%s@myemail.com", uri, uri)
        );
    }
}
