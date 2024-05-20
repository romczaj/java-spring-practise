package pl.romczaj.multidomain.input.api.response;

import java.util.List;
import java.util.UUID;

public record AnnouncementDetailsResponse(
    UUID id,
    String name,
    List<PersonResponse> persons,
    List<MediaFileResponse> mediaFiles) {

    public record PersonResponse(UUID id, String name, String surname) {
    }

    public record MediaFileResponse(UUID id, String title, String description) {
    }
}
