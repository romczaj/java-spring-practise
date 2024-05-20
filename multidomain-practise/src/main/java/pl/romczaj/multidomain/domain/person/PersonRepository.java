package pl.romczaj.multidomain.domain.person;

import java.util.List;
import java.util.UUID;

interface PersonRepository {
    void insert(Person person);

    boolean allExists(List<UUID> ids);

}
