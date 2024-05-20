package pl.romczaj.multidomain.domain.mediafile;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BlobProperty {

    private String blobPrefix = "/component/blob/";
}
