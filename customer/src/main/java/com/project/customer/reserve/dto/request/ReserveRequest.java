package com.project.customer.reserve.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReserveRequest(
        @NotNull
        Long id,
        @NotNull
        LocalDateTime date,
        @NotNull
        Integer timeUnit,
        @NotNull
        Integer price,
        @NotNull
        String payMethod
) {
}
