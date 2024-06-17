package com.project.customer.walkerSearch.controller;

import com.project.core.common.resolver.auth.Authentication;
import com.project.core.common.resolver.queryString.QueryStringResolver;
import com.project.core.common.token.AuthUser;
import com.project.customer.walkerSearch.dto.request.WalkerSearchRequest;
import com.project.customer.walkerSearch.dto.response.WalkerSearchResponse;
import com.project.customer.walkerSearch.service.WalkerSearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v2/walker")
@RequiredArgsConstructor
public class WalkerSearchController {

    private final WalkerSearchService walkerSearchService;

    @GetMapping("/list")
    public ResponseEntity<List<WalkerSearchResponse>> readAll(
            @Authentication @Valid final AuthUser user,
            @QueryStringResolver @Valid final WalkerSearchRequest request,
            @PageableDefault final Pageable pageable) {
        return ResponseEntity.
                status(HttpStatus.OK)
                .body(walkerSearchService.readAll(user, request, pageable));
    }

}
