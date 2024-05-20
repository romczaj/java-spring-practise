package pl.romczaj.graphql.app.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.romczaj.graphql.app.model.Author;
import pl.romczaj.graphql.app.model.Post;
import pl.romczaj.graphql.app.repository.AuthorRepository;
import pl.romczaj.graphql.app.repository.PostRepository;

import java.util.stream.IntStream;

@Configuration
public class InitConfigurator {

    @Bean
    public CommandLineRunner initData(AuthorRepository authorRepository, PostRepository postRepository) {

        return args ->
                IntStream.range(0, 20)
                        .forEach(i -> {
                            Author author = Author.buildTest(i);
                            Author savedAuthor = authorRepository.save(author);

                            Post post = Post.buildTest(i, savedAuthor);
                            postRepository.save(post);
                        });
        }

}
