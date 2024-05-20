package pl.romczaj.security.user;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.stereotype.Component;

import static pl.romczaj.security.security.ApplicationRole.USER;

@Component
public class MockAppAppUserRepository implements AppUserRepository {
    private final List<AppUser> database = List.of(
        new AppUser(1L, "USER_USERNAME", "USER_PASSWORD", USER));

    @Override
    public Option<AppUser> findByUsername(String username) {
        return database.find(appUser -> appUser.username().equals(username));
    }
}
