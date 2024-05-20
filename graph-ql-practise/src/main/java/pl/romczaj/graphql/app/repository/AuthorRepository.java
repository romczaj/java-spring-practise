package pl.romczaj.graphql.app.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.graphql.data.GraphQlRepository;
import org.springframework.stereotype.Repository;
import pl.romczaj.graphql.app.model.Author;

import java.util.List;
import java.util.UUID;

@GraphQlRepository
@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

    @EntityGraph(attributePaths = "posts")
    List<Author> findAllByIdIn(List<UUID> ids);
}