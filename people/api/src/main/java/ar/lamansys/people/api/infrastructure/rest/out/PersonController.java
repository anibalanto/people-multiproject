package ar.lamansys.people.api.infrastructure.rest.out;

import ar.lamansys.people.shared.application.GetPersonByUri;
import ar.lamansys.people.shared.object.PersonSO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("person")
@RequiredArgsConstructor
@RestController
public class PersonController {

    private final GetPersonByUri getPersonByUri;

    @GetMapping("/{uri}")
    public PersonSO getPerson(
            @PathVariable("uri") String uri) {
        return getPersonByUri.run(uri);
    }
}
