package pl.romczaj.graphql.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.graphql.data.GraphQlRepository;
import org.springframework.stereotype.Repository;
import pl.romczaj.graphql.app.model.Post;

import java.util.UUID;

@GraphQlRepository
@Repository
public interface PostRepository extends JpaRepository<Post, UUID>, JpaSpecificationExecutor<Post> {

    @EntityGraph(attributePaths = "author")
    Page<Post> findAll(Specification specification, Pageable pageable);
}
