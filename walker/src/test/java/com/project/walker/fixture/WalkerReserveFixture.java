package com.project.walker.fixture;

import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.reserve.WalkerServiceStatus;
import com.project.core.domain.user.User;

import java.time.LocalDateTime;

public enum WalkerReserveFixture {
    WALKER_RESERVE_ONE(1L, LocalDateTime.now(), 30, WalkerServiceStatus.WALKER_ACCEPT, 1000),
    WALKER_RESERVE_TWO(2L, LocalDateTime.now().minusDays(1), 40, WalkerServiceStatus.WALKER_CHECKING, 2000)
    ;
    private final Long id;
    private User customer;
    private User walker;
    private final LocalDateTime time;
    private final Integer timeUnit;
    private final WalkerServiceStatus status;
    private final Integer price;

    WalkerReserveFixture(final Long id, final LocalDateTime time, final Integer timeUnit, final WalkerServiceStatus status, final Integer price) {
        this.id = id;
        this.time = time;
        this.timeUnit = timeUnit;
        this.status = status;
        this.price = price;
    }

    public WalkerReserve 생성(final User customer, final User walker) {
        return WalkerReserve.builder()
                .id(id)
                .customer(customer)
                .walker(walker)
                .date(time)
                .timeUnit(timeUnit)
                .status(status)
                .price(price)
                .build();
    }
}
