package pl.romczaj.multidomain.domain.person;

import org.mapstruct.Mapper;
import pl.romczaj.multidomain.output.database.PersonEntity;

@Mapper
interface PersonDomainMapper {

    PersonEntity toEntity(Person person);
}
