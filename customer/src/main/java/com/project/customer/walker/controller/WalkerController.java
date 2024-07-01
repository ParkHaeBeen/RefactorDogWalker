package com.project.customer.walker.controller;

import com.project.core.common.resolver.auth.Authentication;
import com.project.core.common.service.dto.Location;
import com.project.core.common.token.AuthUser;
import com.project.customer.walker.dto.response.WalkerInfoResponse;
import com.project.customer.walker.service.WalkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/walker")
@SecurityRequirement(name = "AUTHORIZATION")
public class WalkerController {
    private final WalkerService walkerService;

    @GetMapping("/start")
    @Operation(summary = "서비스 시작 확인")
    public ResponseEntity<Boolean> checkStart(
            @Authentication @Valid final AuthUser user,
            @RequestParam final Long reserveId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(walkerService.checkStart(user, reserveId));
    }

    @GetMapping("/info")
    @Operation(summary = "서비스 시작시 예약 및 walker 정보 조회")
    public ResponseEntity<WalkerInfoResponse> read(
            @Authentication @Valid final AuthUser user,
            @RequestParam final Long reserveId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(walkerService.read(user, reserveId));
    }

    @GetMapping("/now")
    @Operation(summary = "현재 위치 검색")
    public ResponseEntity<List<Location>> readNowLocation(
            @Authentication @Valid final AuthUser user,
            @RequestParam final Long reserveId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(walkerService.readNowLocation(user, reserveId));
    }

    @GetMapping("/location")
    @Operation(summary = "서비스 종료후 경로 조회")
    public ResponseEntity<List<Location>> readLocation(
            @Authentication @Valid final AuthUser user,
            @RequestParam final Long reserveId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(walkerService.readLocation(user, reserveId));
    }
}
