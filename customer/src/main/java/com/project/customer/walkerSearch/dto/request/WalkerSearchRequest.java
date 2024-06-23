package com.project.customer.walkerSearch.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record WalkerSearchRequest(
        String name,
        @NotNull
        Double lnt,
        @NotNull
        Double lat,
        Integer radius
) {

}
