package pl.romczaj.multidomain.input.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.romczaj.multidomain.input.api.request.VerificationResultRequest;
import pl.romczaj.multidomain.input.api.response.MediaFileDescriptionResponse;
import pl.romczaj.multidomain.facade.MediaFileFacade;

@RestController
@RequestMapping("/media-file")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MediaFileController {

    private final MediaFileFacade mediaFileFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(
        @RequestParam("file") MultipartFile file,
        @RequestParam("title") String title,
        @RequestParam("description") String description) {
        mediaFileFacade.addMediaFile(file, title, description);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam("mediaFileId") UUID mediaFileId)  {
        byte[] mediaFileBytes = mediaFileFacade.downloadMediaFile(mediaFileId);
        return ResponseEntity.ok(mediaFileBytes);
    }

    @GetMapping("/description")
    public ResponseEntity<MediaFileDescriptionResponse> getDescription(@RequestParam("mediaFileId") UUID mediaFileId) {
        MediaFileDescriptionResponse response = mediaFileFacade.getMediaFileDescription(mediaFileId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verification-result")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void verificationResult(@RequestBody @Valid @NotNull VerificationResultRequest request) {
        mediaFileFacade.verificationResult(request);
    }

}
