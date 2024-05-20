package pl.romczaj.modulith.book;

import java.util.UUID;

public record BookResponse(
        UUID id,
        String name,
        String author
) {
}
