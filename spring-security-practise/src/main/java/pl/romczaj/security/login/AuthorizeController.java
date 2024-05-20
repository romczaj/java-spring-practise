package pl.romczaj.security.login;

import io.vavr.control.Either;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.romczaj.security.api.ApiResponse;
import pl.romczaj.security.api.FailedOperation;
import pl.romczaj.security.api.ResponseEntityMapper;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authorize")
public class AuthorizeController {

    private final AuthorizeFacade authorizeFacade;

    @PostMapping("/get-token")
    public ResponseEntity<ApiResponse<GetTokenResponse>> getToken(@RequestBody @NotNull @Valid GetTokenRequest getTokenRequest) {

        Either<FailedOperation, GetTokenResponse> getTokenResult = authorizeFacade.getToken(getTokenRequest);
        ApiResponse<GetTokenResponse> getTokenResponseApiResponse = ApiResponse.fromEther(getTokenResult);
        return ResponseEntityMapper.fromApiResponse(getTokenResponseApiResponse);
    }

}