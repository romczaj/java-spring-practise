package pl.romczaj.security.security.jwt;

import java.time.Instant;
import pl.romczaj.security.security.ApplicationRole;

public record JwtClaims(Long userId, ApplicationRole userRole, Instant expirationDate) {
}
