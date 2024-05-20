package pl.romczaj.security.user;

import pl.romczaj.security.security.ApplicationRole;

public record AppUser(Long id, String username, String password, ApplicationRole applicationRole) {
}
