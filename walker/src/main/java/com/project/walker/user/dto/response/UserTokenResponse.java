package com.project.walker.user.dto.response;

import lombok.Builder;

@Builder
public record UserTokenResponse(
        String accessToken,
        String refreshToken
) {
}
