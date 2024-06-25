package com.project.customer.reserve.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.core.common.token.AuthUser;
import com.project.core.domain.reserve.PayHistory;
import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.reserve.WalkerServiceStatus;
import com.project.core.domain.user.User;
import com.project.customer.exception.ErrorCode;
import com.project.customer.exception.ReserveException;
import com.project.customer.exception.user.UserException;
import com.project.customer.kafka.KafkaSender;
import com.project.customer.kafka.Topic;
import com.project.customer.kafka.dto.ReserveDto;
import com.project.customer.reserve.dto.request.ReserveRequest;
import com.project.customer.reserve.dto.response.ReserveDetailResponse;
import com.project.customer.reserve.dto.response.ReserveListResponse;
import com.project.customer.reserve.dto.response.ReserveResponse;
import com.project.customer.reserve.repository.PayHistoryRepository;
import com.project.customer.user.repository.UserRepository;
import com.project.customer.walkerSearch.repository.WalkerReserveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.customer.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReserveService {
    private final WalkerReserveRepository walkerReserveRepository;
    private final PayHistoryRepository payHistoryRepository;
    private final UserRepository userRepository;
    private final KafkaSender sender;

    @Transactional
    public ReserveResponse reserve(final AuthUser user, final ReserveRequest request) {
        if(walkerReserveRepository.findByWalkerIdAndDate(request.id(), request.date()).isPresent()) {
            throw new ReserveException(ErrorCode.RESERVE_ALREAY);
        }

        final User customer = userRepository.findByEmailAndRole(user.email() , user.role())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));

        final User walker = userRepository.findById(request.id())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));

        final WalkerReserve reserve = walkerReserveRepository.save(
                WalkerReserve.builder()
                        .customer(customer)
                        .walker(walker)
                        .date(request.date())
                        .timeUnit(request.timeUnit())
                        .price(request.price())
                        .build()
        );

        final PayHistory payHistory = payHistoryRepository.save(
                PayHistory.builder()
                        .customer(customer)
                        .price(reserve.getPrice())
                        .method(request.payMethod())
                        .reserve(reserve)
                        .build()
        );

        sendReserve(reserve, walker);

        return ReserveResponse.toResponse(reserve, payHistory);
    }

    private void sendReserve(final WalkerReserve reserve, final User walker) {
        try {
            sender.sendReserve(Topic.RESERVE, ReserveDto.builder()
                            .reserveId(reserve.getId())
                            .walkerId(walker.getId())
                            .build()
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void cancel(final AuthUser user, final Long id) {
        final User customer = userRepository.findByEmailAndRole(user.email() , user.role())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));

        final WalkerReserve reserve = walkerReserveRepository.findByCustomerAndId(customer, id)
                .orElseThrow(() -> new ReserveException(NOT_EXIST_RESERVE));

        final PayHistory payHistory = payHistoryRepository.findByReserve(reserve)
                .orElseThrow(() -> new ReserveException(NOT_FOUND_PAY_HISTORY));

        payHistory.refund();
        reserve.changeStatus(WalkerServiceStatus.CUSTOMER_CANCEL);
    }

    public List<ReserveListResponse> readAll(final AuthUser user, final Pageable pageable) {
        final User customer = userRepository.findByEmailAndRole(user.email() , user.role())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));
        return walkerReserveRepository.findByCustomer(customer, pageable).getContent()
                .stream()
                .map(ReserveListResponse::toResponse)
                .toList();
    }

    public ReserveDetailResponse read(final AuthUser user, final Long id) {
        userRepository.findByEmailAndRole(user.email() , user.role())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));

        final WalkerReserve reserve = walkerReserveRepository.findById(id)
                .orElseThrow(() -> new ReserveException(NOT_EXIST_RESERVE));

        return ReserveDetailResponse.toResponse(reserve);
    }
}
