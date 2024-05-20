package pl.romczaj.multidomain.input.api.request;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record AddAnnouncementRequest(String name,
                                     Instant fromDate,
                                     Instant toDate,
                                     List<UUID> mediaFileIds,
                                     List<UUID> personIds) {
}
