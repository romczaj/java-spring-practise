package pl.romczaj.http;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.romczaj.http.githubclient.GithubClient;
import pl.romczaj.http.githubclient.UserApiGithubResponse;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final GithubClient githubClient;

    @GetMapping("/github-users/{username}")
    public UserApiGithubResponse getGithubUser(@PathVariable String username) {
        return githubClient.findUser(username);
    }
}
