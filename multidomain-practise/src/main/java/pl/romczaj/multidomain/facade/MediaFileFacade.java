package pl.romczaj.multidomain.facade;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.romczaj.multidomain.facade.mappers.MediaFileResponseMapper;
import pl.romczaj.multidomain.domain.mediafile.MediaFile;
import pl.romczaj.multidomain.domain.mediafile.MediaFileService;
import pl.romczaj.multidomain.input.api.request.VerificationResultRequest;
import pl.romczaj.multidomain.input.api.response.MediaFileDescriptionResponse;
import pl.romczaj.multidomain.output.externaldrive.ExternalDrive;

@RequiredArgsConstructor
@Component
public class MediaFileFacade {

    private static final MediaFileResponseMapper responseMapper = Mappers.getMapper(MediaFileResponseMapper.class);

    private final MediaFileService mediaFileService;
    private final ExternalDrive externalDrive;
    @Transactional
    public void addMediaFile(MultipartFile file, String title, String description) {
       MediaFile mediaFile = mediaFileService.addFile(title, description);
       externalDrive.saveFile(mediaFile.getBlobPath(), file);
    }

    public byte[] downloadMediaFile(UUID mediaFileId) {
        return mediaFileService.findFile(mediaFileId)
            .map(mf -> externalDrive.getFile(mf.getBlobPath()))
            .orElseThrow(() -> new RuntimeException(String.format("Media file with id %s not found", mediaFileId)));
    }

    public MediaFileDescriptionResponse getMediaFileDescription(UUID mediaFileId) {
        return mediaFileService.findFile(mediaFileId)
            .map(responseMapper::toResponse)
            .orElseThrow(() -> new RuntimeException(String.format("Media file with id %s not found", mediaFileId)));
    }

    @Transactional
    public void verificationResult(VerificationResultRequest request) {
        mediaFileService.findFile(request.mediaFileId())
            .orElseThrow(() -> new RuntimeException(String.format("Media file with id %s not found", request.mediaFileId())));

        mediaFileService.processVerificationResult(request);
    }
}
