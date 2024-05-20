package pl.romczaj.multidomain.input.api.response;

import java.util.UUID;
import pl.romczaj.multidomain.common.MediaFileState;

public record MediaFileDescriptionResponse(UUID id,
                                           String title,
                                           String description,
                                           MediaFileState state) {
}
