package pl.romczaj.graphql.app.model;

import jakarta.persistence.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
import java.util.UUID;

import static jakarta.persistence.AccessType.FIELD;
import static java.util.UUID.randomUUID;


@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Access(FIELD)
@Getter
@Setter
public class Author {

    @Id
    private UUID id;
    private String name;
    private String thumbnail;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    public static Author buildTest(int iteration) {
       return new Author(
               randomUUID(),
                "Author " + iteration,
                "http://example.com/authors/" + iteration,
                null
        );
    }
}
