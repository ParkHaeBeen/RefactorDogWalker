package com.project.walker.walker.dto.response;

import com.project.core.domain.walkerservice.WalkerServiceRoute;

import java.time.LocalDateTime;
import java.util.List;

public record DuringWalkerEndResponse(
        Long reserveId,
        LocalDateTime date,
        List<DuringWalkerLocationResponse> routes
) {
   /* public static DuringWalkerEndResponse toResponse(final WalkerServiceRoute route) {
        return new DuringWalkerEndResponse(
                route.getReserve().getId(),
                route.getCreatedAt(),

        )
    }*/
}
