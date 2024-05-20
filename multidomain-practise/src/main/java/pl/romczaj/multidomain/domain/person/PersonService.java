package pl.romczaj.multidomain.domain.person;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.romczaj.multidomain.input.api.request.AddPersonRequest;

@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;

    public void add(AddPersonRequest addPersonRequest) {
        Person person = Person.createFrom(addPersonRequest);
        personRepository.insert(person);
    }

    public void validateExistingPersons(List<UUID> uuids) {
        if (!personRepository.allExists(uuids)) {
            throw new IllegalArgumentException("Incorrect person ids");
        }
    }
}
