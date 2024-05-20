package pl.romczaj.security.user;

import io.vavr.control.Option;

public interface AppUserRepository {

    Option<AppUser> findByUsername(String username);

}
