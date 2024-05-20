package pl.romczaj.security.security;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Slf4j
public enum ApplicationRole {

    ADMIN(new SimpleGrantedAuthority("ADMIN")),
    USER(new SimpleGrantedAuthority("USER"));

    private final GrantedAuthority grantedAuthority;

    ApplicationRole(GrantedAuthority grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }

    String getAuthority(){
        return this.grantedAuthority.getAuthority();
    }

}
