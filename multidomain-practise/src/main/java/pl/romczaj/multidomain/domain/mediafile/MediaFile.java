package pl.romczaj.multidomain.domain.mediafile;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;
import pl.romczaj.multidomain.common.MediaFileState;
import pl.romczaj.multidomain.input.api.request.VerificationResultRequest;

import static lombok.AccessLevel.PACKAGE;
import static pl.romczaj.multidomain.common.MediaFileState.ACCEPTED;
import static pl.romczaj.multidomain.common.MediaFileState.NEW;
import static pl.romczaj.multidomain.common.MediaFileState.REJECTED;

@AllArgsConstructor(access = PACKAGE)
@Value
public class MediaFile {

    UUID id;
    String title;
    String description;
    String blobPath;
    @With
    MediaFileState mediaFileState;
    String blobPrefix;

    static MediaFile createFrom(String title, String description, String blobPrefix) {
        UUID mediaFileId = UUID.randomUUID();
        return new MediaFile(
            mediaFileId,
            title,
            description,
            blobPrefix + mediaFileId,
            NEW,
            blobPrefix
        );
    }

    MediaFile processVerificationResult(VerificationResultRequest request) {
        if (request.accepted()) {
            return this.withMediaFileState(ACCEPTED);
        }
        return this.withMediaFileState(REJECTED);
    }

}
