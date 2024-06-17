package com.project.walker.user.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserJoinResponse(
        @NotBlank
        String email,
        @NotBlank
        String name,
        @NotBlank
        String refreshToken,
        @NotBlank
        String accessToken
) {
}
