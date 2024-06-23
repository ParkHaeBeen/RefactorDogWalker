package com.project.customer.walkerSearch.dto.response;

import com.project.core.domain.user.walker.WalkerPrice;

public record WalkerPriceResponse(
        Integer timeUnit,
        Integer price
) {
    public static WalkerPriceResponse toResponse(final WalkerPrice walkerPrice) {
        return new WalkerPriceResponse(walkerPrice.getTimeUnit(), walkerPrice.getPrice());
    }
}
