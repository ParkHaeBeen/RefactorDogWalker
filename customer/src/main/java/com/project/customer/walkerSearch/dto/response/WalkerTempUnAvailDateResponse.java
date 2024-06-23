package com.project.customer.walkerSearch.dto.response;

import com.project.core.domain.user.walker.WalkerScheduleTemp;

import java.time.LocalDate;

public record WalkerTempUnAvailDateResponse(
        LocalDate date
) {
    public static WalkerTempUnAvailDateResponse toResponse(final WalkerScheduleTemp walkerScheduleTemp) {
        return new WalkerTempUnAvailDateResponse(walkerScheduleTemp.getUnAvailAt());
    }
}
