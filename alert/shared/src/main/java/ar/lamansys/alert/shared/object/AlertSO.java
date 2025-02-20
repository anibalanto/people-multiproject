package ar.lamansys.alert.shared.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlertSO {
    private String personUri;
    private String kind;
    private String description;
}
