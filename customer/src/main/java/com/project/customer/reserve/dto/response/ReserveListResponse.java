package com.project.customer.reserve.dto.response;

import com.project.core.domain.reserve.WalkerReserve;

import java.time.LocalDateTime;

public record ReserveListResponse(
        Long reserveId,
        LocalDateTime reserveDate,
        Integer timeUnit,
        Integer price
) {
    public static ReserveListResponse toResponse(final WalkerReserve reserve) {
        return new ReserveListResponse(
                reserve.getId(),
                reserve.getDate(),
                reserve.getTimeUnit(),
                reserve.getPrice()
        );
    }
}
