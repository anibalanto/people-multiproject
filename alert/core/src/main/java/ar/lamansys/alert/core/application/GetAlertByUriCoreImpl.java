package ar.lamansys.alert.core.application;

import ar.lamansys.alert.shared.application.GetAlertByUri;
import ar.lamansys.alert.shared.object.AlertSO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class GetAlertByUriCoreImpl implements GetAlertByUri {

    private final GetAlertByPersonUriAndKind getAlertByPersonUriAndKind;

    @Override
    public List<AlertSO> run(String uri) {
        log.info("Consultando la alerta en Core: {}", uri);

        var result = getAlertByPersonUriAndKind.run(uri, "kindA");
        result.addAll(getAlertByPersonUriAndKind.run(uri, "kindB"));

        return result;
    }
}
