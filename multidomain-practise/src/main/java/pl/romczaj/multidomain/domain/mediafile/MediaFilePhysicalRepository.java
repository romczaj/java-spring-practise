package pl.romczaj.multidomain.domain.mediafile;

import jakarta.persistence.EntityManager;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.multidomain.output.database.MediaFileEntity;

@Component
@RequiredArgsConstructor
class MediaFilePhysicalRepository implements MediaFileRepository{

    private final EntityManager entityManager;
    private final MediaFileDomainMapper mapper;

    @Override
    public Optional<MediaFile> findById(UUID mediaFileId) {
        return Optional.ofNullable(entityManager.find(MediaFileEntity.class, mediaFileId)).map(mapper::toDomain);
    }

    @Override
    public MediaFile insert(MediaFile mediaFile) {
        MediaFileEntity entity = mapper.toEntity(mediaFile);
        entityManager.persist(entity);
        return mediaFile;
    }

    @Override
    public void update(MediaFile mediaFile) {
        MediaFileEntity mediaFileEntity = entityManager.find(MediaFileEntity.class, mediaFile.getId());
        mapper.update(mediaFileEntity, mediaFile);
    }
}
