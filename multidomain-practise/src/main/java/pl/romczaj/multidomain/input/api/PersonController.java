package pl.romczaj.multidomain.input.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.romczaj.multidomain.facade.PersonFacade;
import pl.romczaj.multidomain.input.api.request.AddPersonRequest;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonFacade personFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @NotNull @Valid AddPersonRequest request){
        personFacade.add(request);
    }
}
