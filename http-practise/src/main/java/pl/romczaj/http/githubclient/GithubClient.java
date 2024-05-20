package pl.romczaj.http.githubclient;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GithubClient  {

    private final GithubFeignInterface githubFeignInterface;


    public UserApiGithubResponse findUser(String login) {
        try {
            return githubFeignInterface.userDetails(login);
        } catch (FeignException feignException){
            log.error("meh", feignException);
            throw new ApiGithubException("failed");
        }
    }
}
