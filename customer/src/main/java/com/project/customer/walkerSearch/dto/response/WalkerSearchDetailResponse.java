package com.project.customer.walkerSearch.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record WalkerSearchDetailResponse(
        Long id,
        String name,
        Double lat,
        Double lnt,
        List<WalkerPriceResponse> prices,
        List<WalkerPermUnAvailDateResponse> permUnAvailDates,
        List<WalkerTempUnAvailDateResponse> tempUnAvailDates
) {

}
