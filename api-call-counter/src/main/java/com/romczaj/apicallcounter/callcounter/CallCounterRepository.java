package com.romczaj.apicallcounter.callcounter;

import java.util.List;
import java.util.Optional;

public interface CallCounterRepository {

    void insert(CallCounter callCounter);

    Optional<CallCounter> findById(String login);

    void update(CallCounter callCounter);

    List<CallCounter> findAll();

    void deleteAll();
}
