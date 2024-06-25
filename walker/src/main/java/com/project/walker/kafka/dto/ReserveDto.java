package com.project.walker.kafka.dto;

import lombok.Builder;

@Builder
public record ReserveDto(
        Long reserveId,
        Long walkerId
) {
}
