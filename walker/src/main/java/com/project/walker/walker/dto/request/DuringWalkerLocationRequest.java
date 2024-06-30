package com.project.walker.walker.dto.request;

import jakarta.validation.constraints.NotNull;

public record DuringWalkerLocationRequest(
        @NotNull
        Long id,
        @NotNull
        Double lat,
        @NotNull
        Double lnt
) {
}
