package pl.romczaj.multidomain.output.database;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "announcement")
public class AnnouncementEntity {

    @Id
    private UUID id;
    private String name;
    private Instant fromDate;
    private Instant toDate;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcementId")
    private Set<AnnouncementPersonEntity> persons;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcementId")
    private Set<MediaFileEntity> mediaFiles;
}
