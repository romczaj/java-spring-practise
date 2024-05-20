package com.romczaj.apicallcounter.user.githubclient;

import com.romczaj.apicallcounter.exception.FailedOperation;
import feign.FeignException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.romczaj.apicallcounter.exception.FailedOperation.notFoundLogin;
import static com.romczaj.apicallcounter.exception.FailedOperationCode.INTERNAL_SERVER_ERROR;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name="mock-github-client", havingValue="false")
public class PhysicalGithubClient implements GithubClient {

    private final GithubFeignClient githubFeignClient;

    @Override
    public Either<FailedOperation, UserApiGithubResponse> findUser(String login) {
        return Try.of(() -> githubFeignClient.userDetails(login))
            .onSuccess(res -> log.info("Successful retrieve data for login {}", login))
            .toEither()
            .mapLeft(throwable -> mapFailedResult((FeignException) throwable, login));
    }

    private FailedOperation mapFailedResult(FeignException exception, String login) {
        if (exception.status() == HttpStatus.NOT_FOUND.value()) {
            return notFoundLogin(login);
        }
        log.error("Cannot retrieve user from github api", exception);
        return new FailedOperation(INTERNAL_SERVER_ERROR);
    }
}
