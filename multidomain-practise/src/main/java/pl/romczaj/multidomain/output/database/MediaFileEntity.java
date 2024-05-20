package pl.romczaj.multidomain.output.database;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.romczaj.multidomain.common.MediaFileState;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "media_file")
public class MediaFileEntity  {

    @Id
    private UUID id;
    private String title;
    private String description;
    private String blobPath;
    private UUID announcementId;
    @Enumerated(EnumType.STRING)
    private MediaFileState mediaFileState;
}
