package pl.romczaj.security.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static pl.romczaj.security.security.ApplicationRole.ADMIN;
import static pl.romczaj.security.security.ApplicationRole.USER;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http,
                                                      HandlerExceptionAuthenticationEntryPoint handlerExceptionAuthenticationEntryPoint,
                                                      HandlerExceptionFilter handlerExceptionFilter,
                                                      @Qualifier("jwtAuthenticationManager") AuthenticationManager authenticationManager) throws Exception {

        AuthorizationHeaderFilter authorizationHeaderFilter = new AuthorizationHeaderFilter();
        authorizationHeaderFilter.setContinueFilterChainOnUnsuccessfulAuthentication(false);
        authorizationHeaderFilter.setAuthenticationManager(authenticationManager);

        http
            .securityMatcher("/user/**")
            .exceptionHandling().authenticationEntryPoint(handlerExceptionAuthenticationEntryPoint)
            .and()
            .authenticationManager(authenticationManager)
            .authorizeHttpRequests()
            .anyRequest().hasAuthority(USER.getAuthority())
            .and()
            .addFilter(authorizationHeaderFilter)
            .addFilterBefore(handlerExceptionFilter, AuthorizationHeaderFilter.class);

        return http.build();
    }

    @Bean
    public SecurityFilterChain basicAuthSecurityFilterChain(HttpSecurity http,
                                                            @Qualifier("adminAuthenticationManager") AuthenticationManager authenticationManager) throws Exception {

        http
            .securityMatcher("/admin/**")
            .authenticationManager(authenticationManager)
            .authorizeHttpRequests()
            .anyRequest().hasAuthority(ADMIN.getAuthority())
            .and()
            .httpBasic();

        return http.build();
    }

    @Bean
    public SecurityFilterChain publicSecurityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf().disable()
            .securityMatcher("/api/authorize/**", "/actuator/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/**")
            .authorizeHttpRequests()
            .anyRequest().permitAll();

        return http.build();
    }

}
