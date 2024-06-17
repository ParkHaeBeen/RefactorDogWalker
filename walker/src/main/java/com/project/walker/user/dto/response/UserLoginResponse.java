package com.project.walker.user.dto.response;

import lombok.Builder;

@Builder
public record UserLoginResponse(
        String email,
        String name,
        String refreshToken,
        String accessToken
) {
}
