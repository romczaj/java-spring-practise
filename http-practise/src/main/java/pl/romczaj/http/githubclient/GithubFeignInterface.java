package pl.romczaj.http.githubclient;

import feign.Param;
import feign.RequestLine;

interface GithubFeignInterface {

    @RequestLine("GET /{login}")
    UserApiGithubResponse userDetails(@Param("login") String login);
}
