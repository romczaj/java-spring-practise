package pl.romczaj.security.api;

import lombok.Getter;

@Getter
public final class FailedOperation {
    private final FailedOperationCode code;
    private final String message;

    public FailedOperation(FailedOperationCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public FailedOperation(FailedOperationCode code) {
        this.code = code;
        this.message = null;
    }

    public enum FailedOperationCode {
        INCORRECT_CREDENTIALS_,

        OTHER_
    }
}
