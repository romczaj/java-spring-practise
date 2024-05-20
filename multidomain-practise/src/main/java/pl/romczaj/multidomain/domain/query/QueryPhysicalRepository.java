package pl.romczaj.multidomain.domain.query;

import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;
import pl.romczaj.multidomain.input.api.response.AnnouncementDetailsResponse;
import pl.romczaj.multidomain.output.database.AnnouncementEntity;

@RequiredArgsConstructor
@Repository
@Slf4j
public class QueryPhysicalRepository implements QueryRepository {

    private static final AnnouncementDetailsResponseMapper responseMapper = Mappers.getMapper(AnnouncementDetailsResponseMapper.class);
    private final EntityManager entityManager;

    @Override
    public List<AnnouncementDetailsResponse> findAnnouncementsDetails() {

        List<AnnouncementEntity> resultList = entityManager.createQuery("""
                select a from AnnouncementEntity a 
                left join fetch a.mediaFiles
                left join fetch a.persons af
                inner join fetch af.personEntity
                """, AnnouncementEntity.class)
            .getResultList();

        return resultList.stream().map(responseMapper::toResponse).toList();
    }
}
