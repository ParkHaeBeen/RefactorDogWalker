package com.project.customer.user.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UserJoinRequest(
        @NotBlank
        String phoneNumber,
        @NotNull
        Double lat,
        @NotNull
        Double lnt,
        @NotBlank
        String name,
        @NotBlank
        String accessToken,
        @NotNull
        LocalDateTime dogBirth,
        @NotBlank
        String dogName,
        @NotBlank
        String dogType,
        @NotBlank
        String dogDescription
) {
}
