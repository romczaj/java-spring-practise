package pl.romczaj.modulith.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.romczaj.modulith.book.BookExternalApi;
import pl.romczaj.modulith.book.BookResponse;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookExternalApi bookExternalApi;

    @GetMapping("/book/{id}")
    public BookResponse getBook(@PathVariable UUID id){
        return bookExternalApi.getById(id);
    }
}
