package pl.romczaj.http.githubclient;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@ConfigurationProperties(prefix = "github")
@AllArgsConstructor
@Validated
@Getter
public class GithubApiProperties {
    @NotBlank
    String apiUrl;
}
