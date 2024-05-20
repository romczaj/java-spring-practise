package pl.romczaj.modulith.book;

import java.util.UUID;

public interface BookExternalApi {

    BookResponse getById(UUID id);

}
