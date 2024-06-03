package com.romczaj.apicallcounter.callcounter;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
class CallCounterEventService {
    private static final List<String> EVENT_DATABASE = Collections.synchronizedList(new ArrayList<>());
    private static final ReentrantLock MUTEX = new ReentrantLock();

    void saveEvent(String login) {

        try {
            MUTEX.lock();
            EVENT_DATABASE.add(login);
        } finally {
            MUTEX.unlock();
        }
    }

    Map<String, Long> collectWithCleaning() {
        try {
            MUTEX.lock();
            List<String> events = new ArrayList<>(EVENT_DATABASE);
            EVENT_DATABASE.clear();
            return events.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        } finally {
            MUTEX.unlock();
        }
    }

    int listSize(){
        return EVENT_DATABASE.size();
    }

}
