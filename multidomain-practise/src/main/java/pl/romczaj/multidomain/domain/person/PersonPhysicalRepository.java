package pl.romczaj.multidomain.domain.person;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;
import pl.romczaj.multidomain.output.database.PersonEntity;

@Repository
@RequiredArgsConstructor
class PersonPhysicalRepository implements PersonRepository {

    private static final PersonDomainMapper domainMapper = Mappers.getMapper(PersonDomainMapper.class);
    private final EntityManager entityManager;

    @Override
    public void insert(Person person) {
        PersonEntity entity = domainMapper.toEntity(person);
        entityManager.persist(entity);
    }

    @Override
    public boolean allExists(List<UUID> ids) {
        Query query = entityManager.createQuery("""
            select count(p.id) from PersonEntity p where id in :ids
            """);

        query.setParameter("ids", ids);
        long countedIds = (long) query.getSingleResult();
        return countedIds == ids.size();
    }
}
