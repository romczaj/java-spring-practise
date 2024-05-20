package pl.romczaj.multidomain.output.externaldrive;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class ExternalDrive {

    @Value("classpath:2d_car.jpg")
    private Resource exampleFile;

    public void saveFile(String blobPath, MultipartFile file) {
        log.info("File has been saved in external drive {}", blobPath);
    }

    public byte[] getFile(String blobPath) {
        try {
            return FileUtils.readFileToByteArray(exampleFile.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
