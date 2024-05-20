package pl.romczaj.security.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.security.security.ApplicationRole;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private static final String USER_ID = "userId";
    private static final String USER_ROLE = "userRole";

    private final JwtProperties properties;

    public String generateTokenForUser(Long userId, ApplicationRole userRole) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID, userId);
        claims.put(USER_ROLE, userRole);

        return generateToken(claims);
    }

    public JwtClaims getJwtClaimsResult(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return new JwtClaims(
            claims.get(USER_ID, Long.class),
            ApplicationRole.valueOf(claims.get(USER_ROLE, String.class)),
            claims.getExpiration().toInstant()
        );
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(properties.getSecret().getBytes()).parseClaimsJws(token).getBody();
    }

    private String generateToken(Map<String, Object> claims) {
        long epoch = Instant.now().plus(properties.getTokenValidityDuration()).toEpochMilli();
        Date expirationDate = new Date(epoch);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date())
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS256, properties.getSecret().getBytes())
            .compact();
    }
}
