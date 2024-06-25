package com.project.customer.reserve.service;

import com.project.core.common.token.AuthUser;
import com.project.core.domain.reserve.PayHistory;
import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.reserve.WalkerServiceStatus;
import com.project.core.domain.user.User;
import com.project.customer.fixture.AuthUserFixture;
import com.project.customer.fixture.UserFixture;
import com.project.customer.fixture.WalkerReserveFixture;
import com.project.customer.kafka.KafkaSender;
import com.project.customer.reserve.dto.request.ReserveRequest;
import com.project.customer.reserve.dto.response.ReserveDetailResponse;
import com.project.customer.reserve.dto.response.ReserveListResponse;
import com.project.customer.reserve.dto.response.ReserveResponse;
import com.project.customer.reserve.repository.PayHistoryRepository;
import com.project.customer.user.repository.UserRepository;
import com.project.customer.walkerSearch.repository.WalkerReserveRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class ReserveServiceTest {
    @Mock
    private WalkerReserveRepository walkerReserveRepository;

    @Mock
    private PayHistoryRepository payHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private KafkaSender kafkaSender;

    @InjectMocks
    private ReserveService reserveService;

    @Test
    void reserve() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        User user = UserFixture.USER_ONE.생성(authUser);
        User walker = UserFixture.WALKER_ONE.생성();
        ReserveRequest request = ReserveRequest.builder()
                .date(LocalDateTime.now())
                .id(walker.getId())
                .payMethod("CARD")
                .price(1000)
                .timeUnit(30)
                .build();
        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_ONE.생성(user, walker);
        PayHistory payHistory = PayHistory.builder()
                .reserve(reserve)
                .method(request.payMethod())
                .price(request.price())
                .customer(user)
                .build();

        given(walkerReserveRepository.findByWalkerIdAndDate(request.id(), request.date())).willReturn(Optional.empty());
        given(userRepository.findByEmailAndRole(user.getEmail(), user.getRole())).willReturn(Optional.of(user));
        given(userRepository.findById(request.id())).willReturn(Optional.of(walker));
        given(walkerReserveRepository.save(any())).willReturn(reserve);
        given(payHistoryRepository.save(any())).willReturn(payHistory);

        //when
        ReserveResponse response = reserveService.reserve(authUser, request);

        //then
        Assertions.assertThat(response.price()).isEqualTo(request.price());
        Assertions.assertThat(response.timeUnit()).isEqualTo(request.timeUnit());
    }

    @Test
    void cancel() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        User user = UserFixture.USER_ONE.생성(authUser);
        User walker = UserFixture.WALKER_ONE.생성();

        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_ONE.생성(user, walker);

        given(userRepository.findByEmailAndRole(user.getEmail(), user.getRole())).willReturn(Optional.of(user));
        given(walkerReserveRepository.findByCustomerAndId(user, reserve.getId())).willReturn(Optional.of(reserve));

        //when
        reserveService.cancel(authUser, reserve.getId());

        //then
        Assertions.assertThat(reserve.getStatus()).isEqualTo(WalkerServiceStatus.CUSTOMER_CANCEL);
    }

    @Test
    void readAll() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        User user = UserFixture.USER_ONE.생성(authUser);
        User walker = UserFixture.WALKER_ONE.생성();

        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_ONE.생성(user, walker);
        Page<WalkerReserve> reserveList = new PageImpl<>(List.of(reserve));
        given(userRepository.findByEmailAndRole(user.getEmail(), user.getRole())).willReturn(Optional.of(user));
        given(walkerReserveRepository.findByCustomer(user, Pageable.unpaged())).willReturn(reserveList);

        //when
        List<ReserveListResponse> response = reserveService.readAll(authUser, Pageable.unpaged());

        //then
        Assertions.assertThat(response.size()).isEqualTo(reserveList.getSize());
    }

    @Test
    void read() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        User user = UserFixture.USER_ONE.생성(authUser);
        User walker = UserFixture.WALKER_ONE.생성();

        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_ONE.생성(user, walker);
        given(userRepository.findByEmailAndRole(user.getEmail(), user.getRole())).willReturn(Optional.of(user));
        given(walkerReserveRepository.findById(reserve.getId())).willReturn(Optional.of(reserve));

        //when
        ReserveDetailResponse response = reserveService.read(authUser, reserve.getId());

        //then
        Assertions.assertThat(response.reserveId()).isEqualTo(reserve.getId());
        Assertions.assertThat(response.name()).isEqualTo(walker.getName());
        Assertions.assertThat(response.walkerId()).isEqualTo(walker.getId());
    }
}