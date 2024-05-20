package com.romczaj.apicallcounter.callcounter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnMissingBean(CallCounterPhysicalRepository.class)
public class CallCounterInMemoryRepository implements CallCounterRepository {

    private static final  Map<String, CallCounter> DATABASE =  new ConcurrentHashMap<>();

    @Override
    public void insert(CallCounter callCounter) {
        if (findById(callCounter.getLogin()).isPresent()) {
            throw new RuntimeException(String.format("CallerCounter for key %s already exists", callCounter.getLogin()));
        }
        DATABASE.put(callCounter.getLogin(), callCounter);
    }

    @Override
    public Optional<CallCounter> findById(String login) {
        return Optional.ofNullable(DATABASE.get(login));
    }

    @Override
    public void update(CallCounter callCounter) {
        if (findById(callCounter.getLogin()).isEmpty()) {
            throw new RuntimeException(String.format("CallerCounter for key %s not exists", callCounter.getLogin()));
        }
        DATABASE.put(callCounter.getLogin(), callCounter);
    }

    @Override
    public List<CallCounter> findAll() {
        return DATABASE.values().stream().toList();
    }

    @Override
    public void deleteAll() {
        DATABASE.clear();
    }
}
