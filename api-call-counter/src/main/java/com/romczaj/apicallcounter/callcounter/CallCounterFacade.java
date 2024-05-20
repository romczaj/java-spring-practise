package com.romczaj.apicallcounter.callcounter;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Slf4j
public class CallCounterFacade {

    private final CallCounterService callCounterService;
    private final CallCounterEventService callCounterEventService;
    @Async("asyncTaskExecutor")
    public void increaseCounter(String login) {
        callCounterEventService.saveEvent(login);
    }

    @Transactional
    public void refreshCounter() {
        Map<String, Long> collectedCounters = callCounterEventService.collectWithCleaning();
        callCounterService.refreshCounters(collectedCounters);
    }
}
