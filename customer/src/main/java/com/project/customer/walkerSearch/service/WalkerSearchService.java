package com.project.customer.walkerSearch.service;

import com.project.core.common.token.AuthUser;
import com.project.core.domain.user.User;
import com.project.customer.exception.ErrorCode;
import com.project.customer.exception.user.UserException;
import com.project.customer.user.repository.UserRepository;
import com.project.customer.walkerSearch.dto.request.WalkerSearchRequest;
import com.project.customer.walkerSearch.dto.response.WalkerSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.customer.exception.ErrorCode.NOT_EXIST_MEMBER;

@Service
@RequiredArgsConstructor
public class WalkerSearchService {

    private final UserRepository userRepository;

    public List<WalkerSearchResponse> readAll(
            final AuthUser user,
            final WalkerSearchRequest request,
            final Pageable pageable
    ) {
        final User customer = userRepository.findByEmailAndRole(user.email(), user.role())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));

    }
}
