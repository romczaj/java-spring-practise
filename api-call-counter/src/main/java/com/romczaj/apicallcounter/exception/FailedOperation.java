package com.romczaj.apicallcounter.exception;

import java.util.Optional;
import lombok.Value;
import org.springframework.http.ResponseEntity;

import static com.romczaj.apicallcounter.exception.FailedOperationCode.LOGIN_NOT_FOUND;

@Value
public class FailedOperation {
    FailedOperationCode code;
    String message;

    public FailedOperation(FailedOperationCode code) {
        this.code = code;
        this.message = code.getDefaultMessage();
    }

    public FailedOperation(FailedOperationCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public static FailedOperation notFoundLogin(String login) {
        return new FailedOperation(
            LOGIN_NOT_FOUND,
            String.format("Login %s not found", login));
    }

    ResponseEntity<FailedApiResponse> toFailedResponseEntity() {
        return ResponseEntity
            .status(code.getHttpStatus())
            .body(new FailedApiResponse(
                code.name(),
                Optional.of(message).orElse(code.getDefaultMessage()))
            );
    }
}
