package pl.romczaj.multidomain.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.romczaj.multidomain.domain.person.PersonService;
import pl.romczaj.multidomain.input.api.request.AddPersonRequest;

@Component
@RequiredArgsConstructor
public class PersonFacade {

    private final PersonService personService;
    @Transactional
    public void add(AddPersonRequest request) {
        personService.add(request);
    }
}
