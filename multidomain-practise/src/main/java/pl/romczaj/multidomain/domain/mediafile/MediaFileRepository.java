package pl.romczaj.multidomain.domain.mediafile;

import java.util.Optional;
import java.util.UUID;

interface MediaFileRepository {

    Optional<MediaFile> findById(UUID mediaFileId);

    MediaFile insert(MediaFile mediaFile);

    void update(MediaFile mf);
}
