package pl.romczaj.multidomain.facade;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.romczaj.multidomain.domain.announcement.AnnouncementService;
import pl.romczaj.multidomain.domain.mediafile.MediaFileService;
import pl.romczaj.multidomain.domain.person.PersonService;
import pl.romczaj.multidomain.domain.query.QueryRepository;
import pl.romczaj.multidomain.input.api.request.AddAnnouncementRequest;
import pl.romczaj.multidomain.input.api.response.AnnouncementDetailsResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class AnnouncementFacade {

    private final AnnouncementService announcementService;
    private final MediaFileService mediaFileService;
    private final PersonService personService;
    private final QueryRepository queryRepository;


    private final ObjectMapper objectMapper;
    @Transactional
    public void add(AddAnnouncementRequest request) {
        log.info("insert announcement {}", request);
        mediaFileService.validateExistingMediaFiles(request.mediaFileIds());
        personService.validateExistingPersons(request.personIds());
        announcementService.add(request);
    }

    @Transactional(readOnly = true)
    public List<AnnouncementDetailsResponse> getDetails() {
        log.info("retrieve announcement details");
        return queryRepository.findAnnouncementsDetails();
    }
}
