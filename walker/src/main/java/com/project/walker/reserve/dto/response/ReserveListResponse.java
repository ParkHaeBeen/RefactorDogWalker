package com.project.walker.reserve.dto.response;

import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.reserve.WalkerServiceStatus;

import java.time.LocalDateTime;

public record ReserveListResponse(
        Long customerId,
        String name,
        WalkerServiceStatus status,
        Long reserveId,
        LocalDateTime reserveDate,
        Integer timeUnit,
        Integer price
) {
    public static ReserveListResponse toResponse(final WalkerReserve reserve) {
        return new ReserveListResponse(
                reserve.getCustomer().getId(),
                reserve.getCustomer().getName(),
                reserve.getStatus(),
                reserve.getId(),
                reserve.getDate(),
                reserve.getTimeUnit(),
                reserve.getPrice()
        );
    }
}
