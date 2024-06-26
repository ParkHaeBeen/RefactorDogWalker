package com.project.walker.walker.controller;

import com.project.core.common.resolver.auth.Authentication;
import com.project.core.common.resolver.queryString.QueryStringResolver;
import com.project.core.common.token.AuthUser;
import com.project.walker.walker.dto.request.DuringWalkerLocationRequest;
import com.project.walker.walker.dto.request.DuringWalkerStartRequest;
import com.project.walker.walker.dto.response.DuringWalkerEndResponse;
import com.project.walker.walker.service.DuringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/walker")
@RequiredArgsConstructor
@SecurityRequirement(name = "AUTHORIZATION")
public class DuringWalkerController {
    private final DuringService duringService;

    @PostMapping
    @Operation(summary = "서비스 시작")
    public ResponseEntity<Boolean> start(
            @Authentication @Valid final AuthUser user,
            @QueryStringResolver @Valid final DuringWalkerStartRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(duringService.start(user, request));
    }

    @GetMapping
    @Operation(summary = "서비스 시작 확인")
    public ResponseEntity<Boolean> checkStart(
            @Authentication @Valid final AuthUser user,
            @RequestParam final Long reserveId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(duringService.checkStart(user, reserveId));
    }

    @PostMapping("/location")
    @Operation(summary = "위치저장")
    public ResponseEntity<Void> addLocation(
            @Authentication @Valid final AuthUser user,
            @RequestBody @Valid final DuringWalkerLocationRequest request
    ) {
        duringService.addLocation(user, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PatchMapping("/notice")
    @Operation(summary = "고객에게 완료 5분전 알림")
    public ResponseEntity<Void> notice(
            @Authentication @Valid final AuthUser user,
            @RequestParam final Long reserveId
    ) {
        duringService.notice(user, reserveId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/end")
    @Operation(summary = "서비스 종료")
    public ResponseEntity<DuringWalkerEndResponse> end(
            @Authentication @Valid final AuthUser user,
            @RequestParam final Long reserveId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(duringService.end(user, reserveId));
    }
}
