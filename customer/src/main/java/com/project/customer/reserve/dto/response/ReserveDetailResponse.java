package com.project.customer.reserve.dto.response;

import com.project.core.domain.reserve.WalkerReserve;

import java.time.LocalDateTime;

public record ReserveDetailResponse(
        Long reserveId,
        LocalDateTime reserveDate,
        Integer timeUnit,
        Integer price,
        Long walkerId,
        String name
) {
    public static ReserveDetailResponse toResponse(final WalkerReserve reserve) {
        return new ReserveDetailResponse(
                reserve.getId(),
                reserve.getDate(),
                reserve.getTimeUnit(),
                reserve.getPrice(),
                reserve.getWalker().getId(),
                reserve.getWalker().getName()
        );
    }
}
