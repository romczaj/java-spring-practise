package pl.romczaj.modulith.book;

import java.util.UUID;

public record BookDto(
        UUID id,
        String name,
        String author
) {
}