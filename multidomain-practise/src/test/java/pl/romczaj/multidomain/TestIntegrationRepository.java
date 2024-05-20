package pl.romczaj.multidomain;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import pl.romczaj.multidomain.output.database.MediaFileEntity;
import pl.romczaj.multidomain.output.database.PersonEntity;

@RequiredArgsConstructor
public class TestIntegrationRepository {

    private final TestEntityManager entityManager;

    @Transactional(readOnly = true)
    public List<PersonEntity> findAllPersons() {
        return entityManager.getEntityManager().createQuery("""
                select p from PersonEntity p
                """, PersonEntity.class)
            .getResultList();
    }

    @Transactional(readOnly = true)
    public List<MediaFileEntity> findAllMediaFiles() {
        return entityManager.getEntityManager().createQuery("""
                select p from MediaFileEntity p
                """, MediaFileEntity.class)
            .getResultList();
    }
}
