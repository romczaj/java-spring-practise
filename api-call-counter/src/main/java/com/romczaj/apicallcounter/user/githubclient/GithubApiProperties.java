package com.romczaj.apicallcounter.user.githubclient;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@ConfigurationProperties(prefix = "github")
@Validated
public record GithubApiProperties(
        @NotBlank @URL String apiUrl) {
}
