package pl.romczaj.multidomain.input.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.romczaj.multidomain.facade.AnnouncementFacade;
import pl.romczaj.multidomain.input.api.request.AddAnnouncementRequest;
import pl.romczaj.multidomain.input.api.response.AnnouncementDetailsResponse;

@RestController
@RequestMapping("/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementFacade announcementFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @NotNull @Valid AddAnnouncementRequest request){
        announcementFacade.add(request);
    }

    @GetMapping("/details")
    public ResponseEntity<List<AnnouncementDetailsResponse>> getDetails(){
        List<AnnouncementDetailsResponse> response = announcementFacade.getDetails();
        return ResponseEntity.ok(response);
    }
}
