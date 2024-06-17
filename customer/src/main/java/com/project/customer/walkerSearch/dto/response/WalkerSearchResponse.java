package com.project.customer.walkerSearch.dto.response;

public record WalkerSearchResponse(
        Long id,
        String name,
        Double lnt,
        Double lat
) {
}
