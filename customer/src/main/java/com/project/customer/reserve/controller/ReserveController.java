package com.project.customer.reserve.controller;

import com.project.core.common.interceptor.Auth;
import com.project.core.common.resolver.auth.Authentication;
import com.project.core.common.token.AuthUser;
import com.project.customer.reserve.dto.request.ReserveRequest;
import com.project.customer.reserve.dto.response.ReserveDetailResponse;
import com.project.customer.reserve.dto.response.ReserveListResponse;
import com.project.customer.reserve.dto.response.ReserveResponse;
import com.project.customer.reserve.service.ReserveService;
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

    @PostMapping
    @Operation(summary = "예약 진행")
    @Auth
    public ResponseEntity<ReserveResponse> reserve(
            @Authentication  @Valid final AuthUser user,
            @RequestBody @Valid final ReserveRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reserveService.reserve(user, request));
    }

    @DeleteMapping("/{reserveId}")
    @Operation(summary = "예약 취소")
    public ResponseEntity<Void> cancel(
            @Authentication @Valid final AuthUser user,
            @PathVariable final Long reserveId
    ) {
        reserveService.cancel(user, reserveId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "내 예약 리스트 조회")
    public ResponseEntity<List<ReserveListResponse>> readAll(
            @Authentication @Valid final AuthUser user,
            @PageableDefault final Pageable pageable
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reserveService.readAll(user, pageable));
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
}
