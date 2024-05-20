package pl.romczaj.graphql.app;

import graphql.schema.DataFetchingEnvironment;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import pl.romczaj.graphql.app.model.Author;
import pl.romczaj.graphql.app.model.Post;
import pl.romczaj.graphql.app.model.PostInput;
import pl.romczaj.graphql.app.repository.AuthorRepository;
import pl.romczaj.graphql.app.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;
    private final EntityManager em;

    @QueryMapping
    @Transactional(readOnly = true)
    public List<Post> getPosts(@Argument int count, @Argument int offset, DataFetchingEnvironment dataFetchingEnvironment) {
        log.info("get recentPosts");
        PageRequest pageRequest = PageRequest.of(offset, count);
        Page<Post> all = postRepository.findAll(pageRequest);
        return all.toList();
    }

    @MutationMapping
    public Post createPost(@Argument PostInput postInput) {
        Author author = authorRepository.getReferenceById(postInput.authorId());
        Post post = Post.fromInput(postInput, author);
        return postRepository.save(post);
    }

    private  List<Post> dynamicProjectionAttempt(){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
        Root<Post> postRoot = criteriaQuery.from(Post.class);
        postRoot.join("author", JoinType.LEFT);
        criteriaQuery.multiselect(
                postRoot.get("id").alias("id"),
                postRoot.get("title").alias("title"),
                postRoot.get("author").alias("author")  //cannot find how get particular fields
        );

        return em.createQuery(criteriaQuery).getResultList();
    }

}
