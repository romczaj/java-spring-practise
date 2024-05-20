package com.romczaj.apicallcounter.user;

import java.time.Instant;

public record UserApiResponse(
    Integer id,
    String login,
    String name,
    String type,
    String avatarUrl,
    Instant createdAt,
    Double calculations
) {
}
