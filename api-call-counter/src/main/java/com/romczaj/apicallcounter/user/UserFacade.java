package com.romczaj.apicallcounter.user;

import com.romczaj.apicallcounter.user.githubclient.GithubClient;
import com.romczaj.apicallcounter.user.githubclient.UserApiGithubResponse;
import com.romczaj.apicallcounter.exception.FailedOperationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final GithubClient githubClient;

    public UserApiResponse findUser(String login) {
        UserApiGithubResponse user = githubClient.findUser(login);
        return toUserApiResponse(user);
    }

    private UserApiResponse toUserApiResponse(UserApiGithubResponse userApiGithubResponse) {
        return new UserApiResponse(
            userApiGithubResponse.id(),
            userApiGithubResponse.login(),
            userApiGithubResponse.name(),
            userApiGithubResponse.type(),
            userApiGithubResponse.avatarUrl(),
            userApiGithubResponse.createdAt()
        );
    }

}