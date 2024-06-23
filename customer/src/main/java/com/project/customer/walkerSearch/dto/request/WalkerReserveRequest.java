package com.project.customer.walkerSearch.dto.request;

import java.time.LocalDate;

public record WalkerReserveRequest(
        Long id,
        LocalDate date
) {
}
