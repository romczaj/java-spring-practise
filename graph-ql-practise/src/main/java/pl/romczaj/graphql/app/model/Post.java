package pl.romczaj.graphql.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import static jakarta.persistence.AccessType.FIELD;
import static java.util.UUID.randomUUID;


@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Access(FIELD)
@Getter
@Setter
public class Post {

    @Id
    private UUID id;
    private String title;
    private String text;
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_Id")
    private Author author;

    public static Post buildTest(int interation, Author author){
        return new Post(
                randomUUID(),
                "Title Post " + interation,
                "Text Post " + interation,
                "Category Post " + interation,
                author
        );
    }

    public static Post fromInput(PostInput postInput, Author author){
        return new Post(
                randomUUID(),
                postInput.title(),
                postInput.text(),
                postInput.category(),
                author
        );
    }

}
