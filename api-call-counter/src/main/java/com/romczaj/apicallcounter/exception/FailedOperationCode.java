package com.romczaj.apicallcounter.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum FailedOperationCode {
    LOGIN_NOT_FOUND("Login not found", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String defaultMessage;
    private final HttpStatus httpStatus;

    FailedOperationCode(String defaultMessage, HttpStatus httpStatus) {
        this.defaultMessage = defaultMessage;
        this.httpStatus = httpStatus;
    }

}
