package com.romczaj.apicallcounter.user.githubclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GithubOkHttpConfiguration {

    private static final String GITHUB_USERS_URL = "https://api.github.com/users";
    @Bean
    GithubFeignClient githubFeignClient(ObjectMapper objectMapper){
        return Feign.builder()
            .client(new OkHttpClient())
            .encoder(new JacksonEncoder(objectMapper))
            .decoder(new JacksonDecoder(objectMapper))
            .logger(new Slf4jLogger(GithubFeignClient.class))
            .logLevel(Logger.Level.FULL)
            .target(GithubFeignClient.class, GITHUB_USERS_URL);
    }
}
