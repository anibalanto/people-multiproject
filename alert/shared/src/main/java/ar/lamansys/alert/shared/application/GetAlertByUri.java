package ar.lamansys.alert.shared.application;

import ar.lamansys.alert.shared.object.AlertSO;

import java.util.List;

public interface GetAlertByUri {
    List<AlertSO> run(String uri);
}
