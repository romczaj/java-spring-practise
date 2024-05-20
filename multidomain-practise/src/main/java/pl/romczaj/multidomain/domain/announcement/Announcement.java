package pl.romczaj.multidomain.domain.announcement;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Value;
import pl.romczaj.multidomain.input.api.request.AddAnnouncementRequest;

import static lombok.AccessLevel.PACKAGE;

@AllArgsConstructor(access = PACKAGE)
@Value
public class Announcement {

    UUID id;
    String name;
    Instant fromDate;
    Instant toDate;
    List<UUID> mediaFileIds;
    List<UUID> personIds;

    static Announcement createFrom(AddAnnouncementRequest request){
        return new Announcement(
            UUID.randomUUID(),
            request.name(),
            request.fromDate(),
            request.toDate(),
            request.mediaFileIds(),
            request.personIds()
        );
    }
}
