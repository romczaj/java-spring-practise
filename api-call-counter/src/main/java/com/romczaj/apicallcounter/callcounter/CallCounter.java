package com.romczaj.apicallcounter.callcounter;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PACKAGE;

@AllArgsConstructor(access = PACKAGE)
@Getter
class CallCounter {

    private String login;
    private Long requestCount;

    void increaseCounter(Long value) {
        requestCount = requestCount + value;
    }
}
