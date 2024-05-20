package pl.romczaj.multidomain.input.api.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record VerificationResultRequest(@NotNull UUID mediaFileId, @NotNull Boolean accepted) {
}
