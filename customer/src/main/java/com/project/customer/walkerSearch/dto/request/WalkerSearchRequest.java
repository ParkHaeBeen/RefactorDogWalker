package com.project.customer.walkerSearch.dto.request;

import jakarta.validation.constraints.NotNull;

public record WalkerSearchRequest(
        String name,
        @NotNull
        Double lnt,
        @NotNull
        Double lat
) {
}
