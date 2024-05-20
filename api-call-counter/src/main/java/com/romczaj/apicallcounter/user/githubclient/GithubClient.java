package com.romczaj.apicallcounter.user.githubclient;

import com.romczaj.apicallcounter.exception.FailedOperation;
import io.vavr.control.Either;

public interface GithubClient {
    Either<FailedOperation, UserApiGithubResponse> findUser(String login);
}
