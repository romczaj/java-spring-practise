package com.romczaj.apicallcounter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@Getter
public class FailedOperationException extends RuntimeException {

    private final FailedOperation failedOperation;
    ResponseEntity<FailedApiResponse> toFailedResponseEntity(){
        return failedOperation.toFailedResponseEntity();
    }
}
