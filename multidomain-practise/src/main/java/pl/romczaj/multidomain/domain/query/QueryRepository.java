package pl.romczaj.multidomain.domain.query;

import java.util.List;
import pl.romczaj.multidomain.input.api.response.AnnouncementDetailsResponse;

public interface QueryRepository {

    List<AnnouncementDetailsResponse> findAnnouncementsDetails();
}
