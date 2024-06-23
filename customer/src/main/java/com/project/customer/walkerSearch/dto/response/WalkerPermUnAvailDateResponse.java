package com.project.customer.walkerSearch.dto.response;

import com.project.core.domain.user.walker.WalkerSchedulePerm;

public record WalkerPermUnAvailDateResponse(
        String dayOfWeek,
        Integer startTime,
        Integer endTime
) {
    public static WalkerPermUnAvailDateResponse toResponse(final WalkerSchedulePerm walkerSchedulePerm) {
        return new WalkerPermUnAvailDateResponse(
                walkerSchedulePerm.getDayOfWeek(),
                walkerSchedulePerm.getStartTime(),
                walkerSchedulePerm.getEndTime()
        );
    }
}
