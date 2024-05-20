package pl.romczaj.security.login;

import jakarta.validation.constraints.NotBlank;

public record GetTokenRequest(@NotBlank String username, @NotBlank String password) {
}
