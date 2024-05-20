package pl.romczaj.multidomain.domain.announcement;

import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.romczaj.multidomain.output.database.AnnouncementEntity;
import pl.romczaj.multidomain.output.database.AnnouncementPersonEntity;
import pl.romczaj.multidomain.output.database.AnnouncementPersonId;
import pl.romczaj.multidomain.output.database.MediaFileEntity;

@Mapper
interface AnnouncementDomainMapper {

    @Mapping(target = "mediaFileIds", expression = "java(getMediaFileIds(announcementEntity))")
    @Mapping(target = "personIds", expression = "java(getPersonIds(announcementEntity))")
    Announcement toDomain(AnnouncementEntity announcementEntity);

    default List<UUID> getMediaFileIds(AnnouncementEntity announcementEntity) {
        return announcementEntity.getMediaFiles().stream()
            .map(MediaFileEntity::getId)
            .toList();
    }

    default List<UUID> getPersonIds(AnnouncementEntity announcementEntity) {
        return announcementEntity.getPersons().stream()
            .map(AnnouncementPersonEntity::getAnnouncementPersonId)
            .map(AnnouncementPersonId::getPersonId)
            .toList();
    }
}
