package ar.lamansys.alert.infrastructure.rest.out;

import ar.lamansys.alert.core.application.GetAlertByPersonUriAndKind;
import ar.lamansys.alert.shared.application.GetAlertByUri;
import ar.lamansys.alert.shared.object.AlertSO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("alert")
@RequiredArgsConstructor
@RestController
public class AlertController {

    private final GetAlertByPersonUriAndKind getAlertByPersonUriAndKind;
    private final GetAlertByUri getPersonByUri;

    @GetMapping("/person/{uri}")
    public List<AlertSO> getAlerts(
            @PathVariable("uri") String uri,
            @RequestHeader("kind") Optional<String> kind) {
        return kind
                .map(k -> getAlertByPersonUriAndKind.run(uri, k))
                .orElseGet(() -> getPersonByUri.run(uri));
    }

}
