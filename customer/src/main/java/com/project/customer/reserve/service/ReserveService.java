package com.project.customer.reserve.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.core.common.token.AuthUser;
import com.project.core.domain.reserve.PayHistory;
import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.user.User;
import com.project.customer.exception.ErrorCode;
import com.project.customer.exception.ReserveException;
import com.project.customer.exception.user.UserException;
import com.project.customer.kafka.KafkaSender;
import com.project.customer.kafka.Topic;
import com.project.customer.kafka.dto.ReserveDto;
import com.project.customer.reserve.dto.request.ReserveRequest;
import com.project.customer.reserve.dto.response.ReserveResponse;
import com.project.customer.reserve.repository.PayHistoryRepository;
import com.project.customer.user.repository.UserRepository;
import com.project.customer.walkerSearch.repository.WalkerReserveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.customer.exception.ErrorCode.NOT_EXIST_MEMBER;

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

        //TODO:kafka
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

    public void test() {
        try {
            sender.sendReserve(Topic.RESERVE, ReserveDto.builder()
                    .reserveId(2L)
                    .walkerId(1L)
                    .build()
            );
        } catch (JsonProcessingException e) {
            System.out.println("e = " + e);
            log.error("e {} ",e);
            throw new RuntimeException(e);
        }
    }
}
