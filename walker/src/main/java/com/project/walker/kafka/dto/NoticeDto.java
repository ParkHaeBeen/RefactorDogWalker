package com.project.walker.kafka.dto;

import lombok.Builder;

@Builder
public record NoticeDto(
        Long reserveId,
        Long walkerId
) {
}
