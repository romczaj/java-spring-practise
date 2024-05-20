package pl.romczaj.security.login;

import jakarta.validation.constraints.NotBlank;

public record GetTokenResponse(@NotBlank String bearerToken) {
}
