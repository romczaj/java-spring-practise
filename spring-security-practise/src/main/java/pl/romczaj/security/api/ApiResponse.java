package pl.romczaj.security.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.util.Objects.nonNull;
import static pl.romczaj.security.api.ApiResponse.ApiErrorResponse.ApiErrorResponseCode.findByFailedCode;

public record ApiResponse<T>(boolean success,
                             @JsonInclude(NON_NULL) T response,
                             @JsonInclude(NON_NULL) ApiErrorResponse apiErrorResponse) {

    @Getter
    public static final class ApiErrorResponse {
        private final ApiErrorResponseCode code;
        private final String message;

        public ApiErrorResponse(ApiErrorResponseCode code, String message) {
            this.code = code;
            this.message = message;
        }

        public ApiErrorResponse(ApiErrorResponseCode code) {
            this.code = code;
            this.message = code.defaultMessage;
        }

        @AllArgsConstructor
        @Getter
        public enum ApiErrorResponseCode {
            INCORRECT_CREDENTIALS(FailedOperation.FailedOperationCode.INCORRECT_CREDENTIALS_, HttpStatus.UNAUTHORIZED, "User passed incorrect credentials"),
            OTHER(FailedOperation.FailedOperationCode.OTHER_, HttpStatus.BAD_REQUEST, "Other");

            private final FailedOperation.FailedOperationCode failedOperationCode;
            private final HttpStatus httpStatus;
            private final String defaultMessage;

            public static ApiErrorResponseCode findByFailedCode(FailedOperation.FailedOperationCode failedOperationCode) {
                for (ApiErrorResponseCode code : values()) {
                    if (code.failedOperationCode == failedOperationCode) {
                        return code;
                    }
                }

                return OTHER;
            }
        }
    }

    public static <T> ApiResponse<T> fromEther(Either<FailedOperation, T> either) {

        if (either.isRight()) {
            return new ApiResponse<>(true, either.get(), null);
        }

        ApiErrorResponse.ApiErrorResponseCode apiErrorResponseCode = findByFailedCode(either.getLeft().getCode());
        String errorResponseMessage = either.getLeft().getMessage();

        return new ApiResponse<>(
            false,
            null,
            new ApiErrorResponse(
                apiErrorResponseCode,
                nonNull(errorResponseMessage) ? errorResponseMessage : apiErrorResponseCode.defaultMessage));
    }
}
