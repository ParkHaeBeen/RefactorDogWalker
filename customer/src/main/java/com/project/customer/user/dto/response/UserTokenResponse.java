package com.project.customer.user.dto.response;

import lombok.Builder;

@Builder
public record UserTokenResponse(
        String accessToken,
        String refreshToken
) {
}
