package pl.romczaj.security.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static java.util.Collections.singletonList;
import static pl.romczaj.security.security.ApplicationRole.ADMIN;

@Component
@RequiredArgsConstructor
public class AdminAuthenticationManager implements AuthenticationManager {

    private static final String ADMIN_USERNAME = "ADMIN_USERNAME";
    private static final String ADMIN_PASSWORD = "ADMIN_PASSWORD";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();

        if (ADMIN_USERNAME.equals(name) && ADMIN_PASSWORD.equals(password)) {
            return new UsernamePasswordAuthenticationToken(name, password, singletonList(ADMIN.getGrantedAuthority()));
        }

        throw new BadCredentialsException("Incorrect username or password");
    }
}