package com.project.walker.reserve.service;

import com.project.core.common.token.AuthUser;
import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.reserve.WalkerServiceStatus;
import com.project.core.domain.user.User;
import com.project.core.domain.user.customer.CustomerDog;
import com.project.walker.exception.ErrorCode;
import com.project.walker.exception.ReserveException;
import com.project.walker.exception.user.UserException;
import com.project.walker.kafka.Topic;
import com.project.walker.reserve.dto.response.ReserveDetailResponse;
import com.project.walker.reserve.dto.response.ReserveListResponse;
import com.project.walker.reserve.repository.CustomerDogRepository;
import com.project.walker.reserve.repository.WalkerReserveRepository;
import com.project.walker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.walker.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReserveService {
    private final WalkerReserveRepository walkerReserveRepository;
    private final UserRepository userRepository;
    private final CustomerDogRepository customerDogRepository;

    public List<ReserveListResponse> readAll(final AuthUser user, final WalkerServiceStatus status, final Pageable pageable) {
        final User walker = userRepository.findByEmail(user.email())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));

        return walkerReserveRepository.findByWalkerAndStatus(walker, status, pageable)
                .getContent()
                .stream()
                .map(ReserveListResponse::toResponse)
                .toList();
    }

    public ReserveDetailResponse read(final AuthUser user, final Long reserveId) {
        userRepository.findByEmail(user.email())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));

        final WalkerReserve reserve = walkerReserveRepository.findById(reserveId)
                .orElseThrow(() -> new ReserveException(NOT_EXIST_RESERVE));

        final CustomerDog dog = customerDogRepository.findByUserId(reserve.getCustomer().getId())
                .orElseThrow(() -> new UserException(NOT_EXIST_DOG));

        return ReserveDetailResponse.toResponse(reserve, dog);
    }

    @Transactional
    public WalkerServiceStatus accept(final AuthUser user, final Long reserveId, final WalkerServiceStatus status) {
        userRepository.findByEmail(user.email())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));
        final WalkerReserve reserve = walkerReserveRepository.findByIdAndStatus(reserveId, WalkerServiceStatus.WALKER_CHECKING)
                .orElseThrow(() -> new ReserveException(NOT_EXIST_RESERVE));
        reserve.changeStatus(status);

        return reserve.getStatus();
    }

    @Transactional
    public WalkerServiceStatus cancel(final AuthUser user, final Long reserveId) {
        final WalkerReserve reserve = walkerReserveRepository.findByIdAndStatus(reserveId, WalkerServiceStatus.WALKER_ACCEPT)
                .orElseThrow(() -> new ReserveException(NOT_EXIST_RESERVE));
        reserve.changeStatus(WalkerServiceStatus.WALKER_CANCEL);
        return reserve.getStatus();
    }
}
