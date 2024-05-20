package pl.romczaj.multidomain.domain.announcement;

import jakarta.persistence.EntityManager;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;
import pl.romczaj.multidomain.output.database.AnnouncementEntity;
import pl.romczaj.multidomain.output.database.AnnouncementPersonEntity;
import pl.romczaj.multidomain.output.database.AnnouncementPersonId;

@Repository
@RequiredArgsConstructor
public class AnnouncementPhysicalRepository implements AnnouncementRepository {

    private final EntityManager entityManager;
    private static final AnnouncementDomainMapper domainMapper = Mappers.getMapper(AnnouncementDomainMapper.class);

    @Override
    public void insert(Announcement announcement) {
        AnnouncementEntity announcementEntity = new AnnouncementEntity(
            announcement.getId(),
            announcement.getName(),
            announcement.getFromDate(),
            announcement.getToDate(),
            new HashSet<>(),
            new HashSet<>());

        entityManager.persist(announcementEntity);

        announcement.getPersonIds().forEach(
            personId -> {
                AnnouncementPersonEntity announcementMediaFileEntity = new AnnouncementPersonEntity(
                    new AnnouncementPersonId(announcement.getId(), personId), null);
                entityManager.persist(announcementMediaFileEntity);
            }
        );

        announcement.getMediaFileIds().forEach(
            mediaFileId -> {
                entityManager.createQuery("""
                        update MediaFileEntity f set f.announcementId = :announcementId where f.id = :mediaFileId
                        """)
                    .setParameter("announcementId", announcement.getId())
                    .setParameter("mediaFileId", mediaFileId)
                    .executeUpdate();
            }
        );
    }

    @Override
    public Optional<Announcement> findById(UUID id) {
        AnnouncementEntity singleResult = entityManager.createQuery("""
                select a from AnnouncementEntity a 
                left join fetch a.persons
                left join fetch a.mediaFiles
                """, AnnouncementEntity.class)
            .getSingleResult();

        return Optional.ofNullable(singleResult).map(domainMapper::toDomain);
    }

}
