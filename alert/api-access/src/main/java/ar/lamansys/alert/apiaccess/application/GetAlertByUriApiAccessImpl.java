package ar.lamansys.alert.apiaccess.application;

import ar.lamansys.alert.infrastructure.rest.out.AlertController;
import ar.lamansys.alert.shared.application.GetAlertByUri;
import ar.lamansys.alert.shared.object.AlertSO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Slf4j
@Service
@RequiredArgsConstructor
public class GetAlertByUriApiAccessImpl implements GetAlertByUri {

    @Value("${alert.api.base-url}")
    private String alertApiBaseUrl;

    @Value("${alert.kind}")
    private String alertKind;

    private final RestTemplate restTemplate;

    @Override
    public List<AlertSO> run(String uri) {
        String urlRelativa =
                linkTo(
                        methodOn(AlertController.class)
                                .getAlerts(uri, Optional.of(alertKind))
                )
                        .toUri()
                        .getPath();

        try {
            // Construye la URL final de forma segura
            String urlFinal = UriComponentsBuilder
                    .fromHttpUrl(alertApiBaseUrl)
                    .path(urlRelativa)
                    .build()
                    .toUriString();

            // Configura los headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("kind", alertKind);  // Usa el nombre correcto para el header

            // Crea la entidad con los headers
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            log.info("Consultando alertas en AlertAPI con header X-Alert-Kind: {}", alertKind);

            // Realiza la petición con headers personalizados
            ResponseEntity<List> response = restTemplate.exchange(urlFinal, HttpMethod.GET, requestEntity, List.class);

            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }
}
