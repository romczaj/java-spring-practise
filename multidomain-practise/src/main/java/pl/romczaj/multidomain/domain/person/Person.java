package pl.romczaj.multidomain.domain.person;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Value;
import pl.romczaj.multidomain.input.api.request.AddPersonRequest;

import static lombok.AccessLevel.PACKAGE;

@AllArgsConstructor(access = PACKAGE)
@Value
public class Person {

    UUID id;
    String name;
    String surname;


    static Person createFrom(AddPersonRequest request){
        return new Person(
            UUID.randomUUID(),
            request.name(),
            request.surname()
        );
    }
}
