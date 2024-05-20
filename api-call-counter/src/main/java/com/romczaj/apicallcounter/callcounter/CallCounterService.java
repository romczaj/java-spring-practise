package com.romczaj.apicallcounter.callcounter;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class CallCounterService {

    private final CallCounterRepository callCounterRepository;

    public void refreshCounters(Map<String, Long> collectedCounters) {
        collectedCounters.forEach((login, value) -> log.info("Update {} by {}", login, value));
        collectedCounters.forEach(this::increaseCounter);
    }

    private void increaseCounter(String login, Long increaseValue) {
        callCounterRepository.findById(login)
            .ifPresentOrElse(
                callerCounter -> processIncreaseCounter(callerCounter, increaseValue),
                () -> createCounterForLogin(login, increaseValue));
    }

    private void createCounterForLogin(String login, Long increaseValue) {
        CallCounter callCounter = new CallCounter(login, increaseValue);
        callCounterRepository.insert(callCounter);
    }

    private void processIncreaseCounter(CallCounter callCounter, Long increaseValue) {
        callCounter.increaseCounter(increaseValue);
        callCounterRepository.update(callCounter);
    }
}
