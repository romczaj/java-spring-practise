package com.romczaj.apicallcounter.user.githubclient;

import feign.Param;
import feign.RequestLine;

interface GithubFeignClient {

    @RequestLine("GET /{login}")
    UserApiGithubResponse userDetails(@Param("login") String login);
}
