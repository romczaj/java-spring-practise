package pl.romczaj.security.security;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@RequiredArgsConstructor
public class AuthorizationHeaderFilter extends AbstractPreAuthenticatedProcessingFilter {


    @Override
    protected Object getPreAuthenticatedPrincipal(final HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization")).filter(s -> !s.isEmpty()).orElse(null);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(final HttpServletRequest request) {
        return null;
    }
}
