package com.project.customer.reserve.dto.response;

import com.project.core.domain.reserve.PayHistory;
import com.project.core.domain.reserve.WalkerReserve;

import java.time.LocalDateTime;

public record ReserveResponse(
        LocalDateTime reserveDate,
        LocalDateTime payDate,
        Integer timeUnit,
        Integer price
) {
    public static ReserveResponse toResponse(final WalkerReserve reserve, final PayHistory payHistory) {
        return new ReserveResponse(
                reserve.getDate(),
                payHistory.getCreatedAt(),
                reserve.getTimeUnit(),
                reserve.getPrice()
        );
    }
}
