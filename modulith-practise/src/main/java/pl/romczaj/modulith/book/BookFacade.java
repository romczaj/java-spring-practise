package pl.romczaj.modulith.book;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class BookFacade implements BookExternalApi {

    private final Map<UUID, BookDto> database = new HashMap<>();

    @Override
    public BookResponse getById(UUID id) {
        BookDto bookDto = database.get(id);
        return new BookResponse(
                bookDto.id(),
                bookDto.name(),
                bookDto.author()
        );
    }
}
