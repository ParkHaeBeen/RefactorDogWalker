package com.project.customer.walkerSearch.dto.response;

import com.project.core.domain.reserve.WalkerReserve;

import java.time.LocalDateTime;

public record WalkerReserveResponse(
        LocalDateTime date
) {
    public static WalkerReserveResponse toResponse(final WalkerReserve reserve) {
        return new WalkerReserveResponse(reserve.getDate());
    }
}
