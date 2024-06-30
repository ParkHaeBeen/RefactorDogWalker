package com.project.walker.walker.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DuringWalkerStartRequest(
        @NotNull
        Long id,
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
        LocalDateTime date
) {
}
