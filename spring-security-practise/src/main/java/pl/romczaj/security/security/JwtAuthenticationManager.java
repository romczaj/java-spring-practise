package pl.romczaj.security.security;

import io.vavr.control.Either;
import io.vavr.control.Try;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pl.romczaj.security.api.FailedOperation;
import pl.romczaj.security.security.jwt.JwtClaims;
import pl.romczaj.security.security.jwt.JwtUtils;
import pl.romczaj.security.user.AppUserRepository;

import static pl.romczaj.security.api.FailedOperation.FailedOperationCode.INCORRECT_CREDENTIALS_;

@RequiredArgsConstructor
@Component
@Primary
@Slf4j
public class JwtAuthenticationManager implements AuthenticationManager {

    private static final String BEARER_REGEX = "^Bearer ((?:\\.?(?:[A-Za-z0-9-_]+)){3})$";
    private final JwtUtils jwtUtils;
    private final AppUserRepository appUserRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String authorizationHeaderValue = (String) authentication.getPrincipal();
        if (!authorizationHeaderValue.matches(BEARER_REGEX)) {
            throw new BadCredentialsException("Incorrect token");
        }

        return validateToken(authorizationHeaderValue)
            .map(jwtClaims -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(jwtClaims.userRole().getAuthority());
                return new UsernamePasswordAuthenticationToken(jwtClaims.userId(), null, List.of(grantedAuthority));
            })
            .getOrElseThrow(() -> new BadCredentialsException("Incorrect authentication"));

    }

    private Either<FailedOperation, JwtClaims> validateToken(String authorizationHeaderValue) {
        String bearerToken = authorizationHeaderValue.substring(7);
        return Try.of(() -> jwtUtils.getJwtClaimsResult(bearerToken))
            .toEither(() -> new FailedOperation(INCORRECT_CREDENTIALS_));
    }
}
