package pl.romczaj.multidomain.domain.mediafile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.romczaj.multidomain.input.api.request.VerificationResultRequest;

@Service
@RequiredArgsConstructor
public class MediaFileService {

    private final MediaFileRepository mediaFileRepository;
    private final BlobProperty blobProperty;

    public MediaFile addFile(String title, String description) {
        MediaFile mediaFile = MediaFile.createFrom(title, description, blobProperty.getBlobPrefix());
        return mediaFileRepository.insert(mediaFile);
    }

    public Optional<MediaFile> findFile(UUID mediaFileId) {
        return mediaFileRepository.findById(mediaFileId);
    }

    public void processVerificationResult(VerificationResultRequest request) {
        mediaFileRepository.findById(request.mediaFileId())
            .ifPresent(mf -> {
                MediaFile updatedMediaFile = mf.processVerificationResult(request);
                mediaFileRepository.update(updatedMediaFile);
            });
    }

    public void validateExistingMediaFiles(List<UUID> uuids) {
    }
}
