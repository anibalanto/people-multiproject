package ar.lamansys.people.apiaccess.application;

import ar.lamansys.people.api.infrastructure.rest.out.PersonController;
import ar.lamansys.people.shared.application.GetPersonByUri;
import ar.lamansys.people.shared.object.PersonSO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Slf4j
@Service
@RequiredArgsConstructor
public class GetPersonByUriApiAccessImpl implements GetPersonByUri {

    @Value("${people.api.base-url}")
    private String peopleApiBaseUrl;

    private final RestTemplate restTemplate;

    @Override
    public PersonSO run(String uri) {
        String urlRelativa = linkTo(methodOn(PersonController.class)
                .getPerson(uri)).toUri().getPath();
        try {
            // Construye la URL final de forma segura
            String urlFinal = UriComponentsBuilder
                    .fromHttpUrl(peopleApiBaseUrl)
                    .path(urlRelativa)
                    .build()
                    .toUriString();

            log.info("Consultando persona en PeopleAPI: {}", urlFinal);
            return restTemplate.getForObject(urlFinal, PersonSO.class);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }
}
