package pl.romczaj.multidomain.domain.announcement;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.romczaj.multidomain.input.api.request.AddAnnouncementRequest;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    public void add(AddAnnouncementRequest request) {
        Announcement announcement = Announcement.createFrom(request);
        announcementRepository.insert(announcement);
    }

    public Optional<Announcement> findById(UUID id){
        return announcementRepository.findById(id);
    }
}
