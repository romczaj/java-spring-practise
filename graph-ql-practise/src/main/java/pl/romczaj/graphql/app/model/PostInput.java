package pl.romczaj.graphql.app.model;


import java.util.UUID;

public record PostInput(
        String title,
        String text,
        String category,
        UUID authorId
) {
}
