package pl.romczaj.multidomain.domain.announcement;

import java.util.Optional;
import java.util.UUID;

interface AnnouncementRepository {
    void insert(Announcement announcement);

    Optional<Announcement> findById(UUID id);
}
