package com.romczaj.apicallcounter.callcounter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CallCounterScheduler {

    private final CallCounterFacade callCounterFacade;

    @Scheduled(fixedRate = 10000)
    public void refreshCounters() {
        log.info("Refresh counters");
        callCounterFacade.refreshCounter();
    }
}
