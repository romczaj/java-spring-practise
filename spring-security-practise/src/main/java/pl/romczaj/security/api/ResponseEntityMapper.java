package pl.romczaj.security.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityMapper {

    public static <T> ResponseEntity<ApiResponse<T>> fromApiResponse(ApiResponse<T> response) {
        return ResponseEntityMapper.fromApiResponse(response, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> fromApiResponse(ApiResponse<T> response, HttpStatus successResultCode) {

        if (response.success()) {
            return new ResponseEntity<>(response, successResultCode);
        }

        return new ResponseEntity<>(response, response.apiErrorResponse().getCode().getHttpStatus());
    }
}
