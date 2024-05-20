package com.romczaj.apicallcounter.callcounter;

import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
class CallCounterEventService {
    private static final List<String> EVENT_DATABASE = Collections.synchronizedList(new ArrayList<>());
    private static final ReentrantLock MUTEX = new ReentrantLock();

    void saveEvent(String login) {
        Try.run(() -> {
            MUTEX.lock();
            EVENT_DATABASE.add(login);
        }).andFinally(
            MUTEX::unlock
        );
    }

    Map<String, Long> collectWithCleaning() {
        return Try.of(() -> {
                MUTEX.lock();
                List<String> events = new ArrayList<>(EVENT_DATABASE);
                EVENT_DATABASE.clear();
                return events;
            }).andFinally(
                MUTEX::unlock
            )
            .map(events -> events.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())))
            .getOrElse(new HashMap<>());
    }

    int listSize(){
        return EVENT_DATABASE.size();
    }

}
