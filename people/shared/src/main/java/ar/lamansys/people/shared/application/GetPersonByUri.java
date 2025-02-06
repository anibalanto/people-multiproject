package ar.lamansys.people.shared.application;

import ar.lamansys.people.shared.object.PersonSO;

public interface GetPersonByUri {
    PersonSO run(String uri);
}
