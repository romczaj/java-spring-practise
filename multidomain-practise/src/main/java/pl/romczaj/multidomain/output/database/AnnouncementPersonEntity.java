package pl.romczaj.multidomain.output.database;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "announcement_person")
public class AnnouncementPersonEntity {

    @EmbeddedId
    private AnnouncementPersonId announcementPersonId;

    @OneToOne
    @JoinColumn(name = "personId", referencedColumnName = "id", insertable = false, updatable = false)
    private PersonEntity personEntity;
}
