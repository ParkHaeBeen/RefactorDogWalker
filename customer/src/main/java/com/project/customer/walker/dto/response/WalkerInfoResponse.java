package com.project.customer.walker.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WalkerInfoResponse(
        String walkerName,
        String dogName,
        LocalDateTime date
) {
}
