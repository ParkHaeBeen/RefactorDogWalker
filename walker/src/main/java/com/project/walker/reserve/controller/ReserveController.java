package com.project.walker.reserve.controller;

import com.project.core.common.resolver.auth.Authentication;
import com.project.core.common.token.AuthUser;
import com.project.core.domain.reserve.WalkerServiceStatus;
import com.project.walker.reserve.dto.response.ReserveDetailResponse;
import com.project.walker.reserve.dto.response.ReserveListResponse;
import com.project.walker.reserve.service.ReserveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/reserve")
@RequiredArgsConstructor
@SecurityRequirement(name = "AUTHORIZATION")
public class ReserveController {
    private final ReserveService reserveService;

    @GetMapping
    @Operation(summary = "예약 조회")
    public ResponseEntity<List<ReserveListResponse>> readAll(
            @Authentication @Valid final AuthUser user,
            @RequestParam final WalkerServiceStatus status,
            @PageableDefault final Pageable pageable
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reserveService.readAll(user, status, pageable));
    }

    @GetMapping("/{reserveId}")
    @Operation(summary = "예약 상세 조회")
    public ResponseEntity<ReserveDetailResponse> read(
            @Authentication @Valid final AuthUser user,
            @PathVariable final Long reserveId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reserveService.read(user, reserveId));
    }

    @PatchMapping("/{reserveId}")
    @Operation(summary = "예약 수락 or 거절")
    public ResponseEntity<WalkerServiceStatus> accept(
            @Authentication @Valid final AuthUser user,
            @PathVariable final Long reserveId,
            @RequestParam final WalkerServiceStatus status
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reserveService.changeStatus(user, reserveId, status));
    }

    @DeleteMapping("/{reserveId}")
    @Operation(summary = "예약 취소")
    public ResponseEntity<WalkerServiceStatus> cancel(
            @Authentication @Valid final AuthUser user,
            @PathVariable final Long reserveId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reserveService.cancel(user, reserveId));
    }

}
