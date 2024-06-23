package com.project.customer.walkerSearch.dto.response;

import com.project.core.domain.user.User;

public record WalkerSearchResponse(
        Long id,
        String name,
        Double lnt,
        Double lat
) {
    public static WalkerSearchResponse toResponse(final User user) {
        return new WalkerSearchResponse(
                user.getId(),
                user.getName(),
                user.getLocation().getX(),
                user.getLocation().getY()
        );
    }
}
