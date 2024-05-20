package pl.romczaj.security.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static pl.romczaj.security.api.ApiResponse.ApiErrorResponse.ApiErrorResponseCode.INCORRECT_CREDENTIALS;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentialsException(AuthenticationException e) {
        ApiResponse<Void> apiResponse = new ApiResponse(false, null, new ApiResponse.ApiErrorResponse(INCORRECT_CREDENTIALS));
        return ResponseEntityMapper.fromApiResponse(apiResponse);
    }
}
