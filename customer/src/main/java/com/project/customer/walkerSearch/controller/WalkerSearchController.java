package com.project.customer.walkerSearch.controller;

import com.project.core.common.resolver.auth.Authentication;
import com.project.core.common.resolver.queryString.QueryStringResolver;
import com.project.core.common.token.AuthUser;
import com.project.customer.walkerSearch.dto.request.WalkerReserveRequest;
import com.project.customer.walkerSearch.dto.request.WalkerSearchRequest;
import com.project.customer.walkerSearch.dto.response.WalkerReserveResponse;
import com.project.customer.walkerSearch.dto.response.WalkerSearchDetailResponse;
import com.project.customer.walkerSearch.dto.response.WalkerSearchResponse;
import com.project.customer.walkerSearch.service.WalkerSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v2/walker")
@RequiredArgsConstructor
@SecurityRequirement(name = "AUTHORIZATION")
public class WalkerSearchController {

    private final WalkerSearchService walkerSearchService;

    @GetMapping("/list")
    @Operation(summary = "walker 검색")
    public ResponseEntity<List<WalkerSearchResponse>> readAll(
            @Authentication @Valid final AuthUser user,
            @QueryStringResolver @Valid final WalkerSearchRequest request,
            @PageableDefault final Pageable pageable) {
        return ResponseEntity.
                status(HttpStatus.OK)
                .body(walkerSearchService.readAll(user, request, pageable));
    }

    @GetMapping
    @Operation(summary = "해당 walker 검색")
    public ResponseEntity<WalkerSearchDetailResponse> read(
            @Authentication @Valid final AuthUser user,
            @RequestParam final Long id
    ) {
        return ResponseEntity.
                status(HttpStatus.OK)
                .body(walkerSearchService.read(user, id));
    }

    @GetMapping("/reserve")
    @Operation(summary = "해당 날짜 walker 예약 전송")
    public ResponseEntity<List<WalkerReserveResponse>> readReserve(
            @Authentication @Valid final AuthUser user,
            @QueryStringResolver final WalkerReserveRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(walkerSearchService.readReserve(user, request));
    }

}
