package com.project.walker.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record UserJoinRequest(
        @NotBlank
        @Pattern(regexp = "^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$")
        String phoneNumber,
        @NotNull
        Double lat,
        @NotNull
        Double lnt,
        @NotBlank
        String name,
        //TEMP : @NotBlank 없애기
        String token,
        List<UserJoinScheduleRequest> schedule,
        @NotNull
        List<UserJoinPriceRequest> price,
        //TEMP: Oauth없이 회원가입할 수 있게
        String email
) {
}
