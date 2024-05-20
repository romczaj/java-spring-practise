package pl.romczaj.security.security.jwt;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Value
@NonFinal
@ConfigurationProperties(prefix = "jwt")
@Validated
@RequiredArgsConstructor
class JwtProperties {

    @NotBlank
    String secret;
    @NotNull
    Duration tokenValidityDuration;
}