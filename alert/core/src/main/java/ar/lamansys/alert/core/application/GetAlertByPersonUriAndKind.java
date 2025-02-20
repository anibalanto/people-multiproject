package ar.lamansys.alert.core.application;

import ar.lamansys.alert.shared.object.AlertSO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class GetAlertByPersonUriAndKind {

    public List<AlertSO> run(String uri, String kind) {
        log.info("Consultando persona en Core: {}", uri);
        var list = new ArrayList<AlertSO>();
        list.add(new AlertSO(
                uri,
                kind,
                String.format("Alerta 1 de uri %s, kind %s", uri, kind)
        ));
        list.add(new AlertSO(
                uri,
                kind,
                String.format("Alerta 2 de uri %s, kind %s", uri, kind)
        ));
        list.add(new AlertSO(
                uri,
                kind,
                String.format("Alerta 3 de uri %s, kind %s", uri, kind)
        ));
        return list;
    }
}
