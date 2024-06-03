package com.romczaj.apicallcounter.user.githubclient;

import feign.Param;
import feign.RequestLine;

interface GithubFeignInterface {

    @RequestLine("GET /{login}")
    UserApiGithubResponse userDetails(@Param("login") String login);
}
