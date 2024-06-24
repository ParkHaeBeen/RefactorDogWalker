package com.project.customer.reserve.controller;

import com.project.core.common.resolver.auth.Authentication;
import com.project.core.common.token.AuthUser;
import com.project.customer.reserve.dto.request.ReserveRequest;
import com.project.customer.reserve.dto.response.ReserveResponse;
import com.project.customer.reserve.service.ReserveService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final ReserveService reserveService;

    @PostMapping
    @Operation(summary = "예약 진행")
    public ResponseEntity<ReserveResponse> reserve(
            @Authentication @Valid final AuthUser user,
            @RequestBody @Valid final ReserveRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reserveService.reserve(user, request));
    }

    @GetMapping
    @Operation(summary = "test")
    public ResponseEntity<Boolean> test() {
        reserveService.test();
        return ResponseEntity.ok(true);
    }
}
