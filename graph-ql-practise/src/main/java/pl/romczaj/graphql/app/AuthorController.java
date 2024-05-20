package pl.romczaj.graphql.app;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import pl.romczaj.graphql.app.model.Author;
import pl.romczaj.graphql.app.repository.AuthorRepository;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;

    @QueryMapping
    @Transactional(readOnly = true)
    public List<Author> getAuthors(@Argument int count, @Argument int offset) {
        Page<Author> all = authorRepository.findAll(PageRequest.of(offset, count));
        List<UUID> authorIds = all.map(Author::getId).toList();
        return authorRepository.findAllByIdIn(authorIds);
    }
}
