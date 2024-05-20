package pl.romczaj.multidomain.domain.query;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.romczaj.multidomain.input.api.response.AnnouncementDetailsResponse;
import pl.romczaj.multidomain.input.api.response.AnnouncementDetailsResponse.PersonResponse;
import pl.romczaj.multidomain.output.database.AnnouncementEntity;
import pl.romczaj.multidomain.output.database.AnnouncementPersonEntity;
import pl.romczaj.multidomain.output.database.PersonEntity;

@Mapper
interface AnnouncementDetailsResponseMapper {

    @Mapping(target = "persons", expression = "java(retrievePersons(announcementEntity))")
    AnnouncementDetailsResponse toResponse(AnnouncementEntity announcementEntity);
    PersonResponse toResponse(PersonEntity announcementEntity);

    default List<PersonResponse> retrievePersons(AnnouncementEntity announcement){
        return announcement.getPersons().stream()
            .map(AnnouncementPersonEntity::getPersonEntity)
            .map(this::toResponse).toList();
    }
}
