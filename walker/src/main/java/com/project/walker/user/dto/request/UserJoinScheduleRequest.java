package com.project.walker.user.dto.request;

public record UserJoinScheduleRequest (
        String dayOfWeek,
        Integer startTime,
        Integer endTime
) {}