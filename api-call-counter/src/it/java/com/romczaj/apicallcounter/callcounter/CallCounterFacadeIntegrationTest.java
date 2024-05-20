package com.romczaj.apicallcounter.callcounter;

import com.romczaj.apicallcounter.BaseIntegrationTest;
import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class CallCounterFacadeIntegrationTest extends BaseIntegrationTest {

    @Autowired
    CallCounterFacade callCounterFacade;

    @Autowired
    CallCounterScheduler callCounterScheduler;

    @Autowired
    ThreadPoolTaskExecutor asyncTaskExecutor;

    @Autowired
    CallCounterEventService callCounterEventService;

    static List<String> LOGINS = List.of("login1", "login2", "login3", "login4", "login5", "login6");
    static int ITERATION_SIZE = 1000;

    @Test
    @DisplayName("Should save all events api invocation for different users, count them and save results")
    void shouldSaveCounterResult() throws InterruptedException {

        addEvents();
        callCounterScheduler.refreshCounters();
        addEvents();
        callCounterScheduler.refreshCounters();
        addEvents();

        while (eventsInSaving()) {
            wait(100);
        }

        callCounterScheduler.refreshCounters();

        List<CallCounter> callCounterEntities = callCounterRepository.findAll();
        assertEquals(LOGINS.size(), callCounterRepository.findAll().size());
        callCounterEntities.forEach(callerCounter -> {
                assertEquals(ITERATION_SIZE * 3L, callerCounter.getRequestCount());
            }
        );
    }

    private void addEvents(){
        LOGINS.forEach(login ->
            IntStream.range(0, ITERATION_SIZE).forEach(i -> callCounterFacade.increaseCounter(login)));
    }

    private boolean eventsInSaving() {
        int executorSize = asyncTaskExecutor.getThreadPoolExecutor().getQueue().size();
        int listSize = callCounterEventService.listSize();
        log.info("Left events in task executor {}, left events is list {}", executorSize, listSize);
        return executorSize > 0 ;
    }

    private void wait(int milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }
}
