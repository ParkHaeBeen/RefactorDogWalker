package com.project.walker.reserve.dto.response;

import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.reserve.WalkerServiceStatus;
import com.project.core.domain.user.customer.CustomerDog;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReserveDetailResponse(
        Long reserveId,
        LocalDateTime reserveDate,
        Integer timeUnit,
        Integer price,
        WalkerServiceStatus status,
        Long customerId,
        String customerName,
        String dogName,
        String type,
        String description,
        String imgUrl,
        LocalDate birth
) {
    public static ReserveDetailResponse toResponse(final WalkerReserve reserve, final CustomerDog dog) {
        return new ReserveDetailResponse(
                reserve.getId(),
                reserve.getDate(),
                reserve.getTimeUnit(),
                reserve.getPrice(),
                reserve.getStatus(),
                reserve.getCustomer().getId(),
                reserve.getCustomer().getName(),
                dog.getName(),
                dog.getType(),
                dog.getDescription(),
                dog.getImgUrl(),
                dog.getBirth()
        );
    }
}
