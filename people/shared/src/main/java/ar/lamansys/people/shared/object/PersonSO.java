package ar.lamansys.people.shared.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonSO {
    private String uri;
    private String name;
    private String lastName;
    private String email;
}
