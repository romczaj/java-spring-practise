package pl.romczaj.security.login;

import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.security.api.FailedOperation;
import pl.romczaj.security.security.jwt.JwtUtils;
import pl.romczaj.security.user.AppUser;
import pl.romczaj.security.user.AppUserRepository;

import static pl.romczaj.security.api.FailedOperation.FailedOperationCode.INCORRECT_CREDENTIALS_;

@Component
@RequiredArgsConstructor
public class AuthorizeFacade {

    private final JwtUtils jwtUtils;
    private final AppUserRepository appUserRepository;

    public Either<FailedOperation, GetTokenResponse> getToken(GetTokenRequest getTokenRequest) {
        return validateCredentials(getTokenRequest)
            .map(appUser -> jwtUtils.generateTokenForUser(appUser.id(), appUser.applicationRole()))
            .map(GetTokenResponse::new);
    }

    private Either<FailedOperation, AppUser> validateCredentials(GetTokenRequest getTokenRequest) {
        return appUserRepository.findByUsername(getTokenRequest.username())
            .filter(u -> u.password().equals(getTokenRequest.password()))
            .toEither(() -> new FailedOperation(INCORRECT_CREDENTIALS_));
    }
}
