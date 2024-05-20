package com.romczaj.apicallcounter.callcounter;

import com.romczaj.apicallcounter.entity.CallCounterEntity;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name="mock-repository", havingValue="false")
class CallCounterPhysicalRepository implements CallCounterRepository {

    private final EntityManager entityManager;

    @Override
    public void insert(CallCounter callCounter) {
        CallCounterEntity entity = toEntity(callCounter);
        entityManager.persist(entity);
    }

    @Override
    public Optional<CallCounter> findById(String login) {
        return Optional.ofNullable(entityManager.find(CallCounterEntity.class, login))
            .map(this::fromEntity);
    }

    @Override
    public void update(CallCounter callCounter) {
        Optional.ofNullable(entityManager.find(CallCounterEntity.class, callCounter.getLogin()))
            .ifPresent(entity -> {
                entity.updateRequestCount(callCounter.getRequestCount());
                entityManager.merge(entity);
            });
    }

    @Override
    public List<CallCounter> findAll() {
        return entityManager.createQuery("""
                select v from CallCounterEntity v
                """, CallCounterEntity.class)
            .getResultList().stream().map(this::fromEntity).toList();
    }

    @Override
    public void deleteAll() {

    }

    private CallCounterEntity toEntity(CallCounter callCounter) {
        return new CallCounterEntity(
            callCounter.getLogin(),
            callCounter.getRequestCount()
        );
    }

    private CallCounter fromEntity(CallCounterEntity entity) {
        return new CallCounter(
            entity.getLogin(),
            entity.getRequestCount()
        );
    }

}
