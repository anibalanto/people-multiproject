package ar.lamansys.people.shared.object;

import ar.sharepath.dynamicvalidation.core.DynamicValidation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DynamicValidation
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonSO {
    private String uri;
    private String name;
    private String lastName;
    private String email;
    private Integer age;
}
