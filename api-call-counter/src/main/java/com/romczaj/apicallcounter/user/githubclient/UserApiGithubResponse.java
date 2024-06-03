package com.romczaj.apicallcounter.user.githubclient;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.Instant;

@JsonNaming(SnakeCaseStrategy.class)
public record UserApiGithubResponse(
        String login,
        Integer id,
        String name,
        String type,
        String avatarUrl,
        Instant createdAt,
        Integer followers,
        Integer publicRepos) {
}
