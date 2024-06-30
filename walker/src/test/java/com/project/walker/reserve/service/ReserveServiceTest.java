package com.project.walker.reserve.service;

import com.project.core.common.token.AuthUser;
import com.project.core.domain.reserve.PayHistory;
import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.reserve.WalkerServiceStatus;
import com.project.core.domain.user.User;
import com.project.core.domain.user.customer.CustomerDog;
import com.project.walker.fixture.AuthUserFixture;
import com.project.walker.fixture.UserFixture;
import com.project.walker.fixture.WalkerReserveFixture;
import com.project.walker.reserve.dto.response.ReserveDetailResponse;
import com.project.walker.reserve.dto.response.ReserveListResponse;
import com.project.walker.repository.CustomerDogRepository;
import com.project.walker.repository.PayHistoryRepository;
import com.project.walker.repository.WalkerReserveRepository;
import com.project.walker.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class ReserveServiceTest {

    @Mock
    private WalkerReserveRepository walkerReserveRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomerDogRepository customerDogRepository;

    @Mock
    private PayHistoryRepository payHistoryRepository;

    @InjectMocks
    private ReserveService reserveService;

    @Test
    void readAll() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        User walker = UserFixture.WALKER_ONE.생성(authUser);
        User user = UserFixture.USER_ONE.생성();
        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_TWO.생성(user, walker);

        Page<WalkerReserve> reserveList = new PageImpl<>(List.of(reserve));

        given(userRepository.findByEmail(walker.getEmail())).willReturn(Optional.of(walker));
        given(walkerReserveRepository.findByWalkerAndStatus(walker, WalkerServiceStatus.WALKER_CHECKING, Pageable.unpaged())).willReturn(reserveList);

        //when
        List<ReserveListResponse> response = reserveService.readAll(authUser, WalkerServiceStatus.WALKER_CHECKING, Pageable.unpaged());

        //then
        Assertions.assertThat(response.size()).isEqualTo(reserveList.getSize());
    }

    @Test
    void read() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        User walker = UserFixture.WALKER_ONE.생성(authUser);
        User user = UserFixture.USER_ONE.생성();
        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_TWO.생성(user, walker);
        CustomerDog customerDog = CustomerDog.builder()
                .userId(user.getId())
                .name("dog")
                .description("dog")
                .type("maltiz")
                .imgUrl("img")
                .birth(LocalDate.now())
                .build();

        given(userRepository.findByEmail(walker.getEmail())).willReturn(Optional.of(walker));
        given(walkerReserveRepository.findById(reserve.getId())).willReturn(Optional.of(reserve));
        given(customerDogRepository.findByUserId(user.getId())).willReturn(Optional.of(customerDog));
        //when
        ReserveDetailResponse response = reserveService.read(authUser, reserve.getId());

        //then
        Assertions.assertThat(response.reserveId()).isEqualTo(reserve.getId());
        Assertions.assertThat(response.customerId()).isEqualTo(user.getId());
        Assertions.assertThat(response.dogName()).isEqualTo(customerDog.getName());
    }

    @Test
    void accept() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        User walker = UserFixture.WALKER_ONE.생성(authUser);
        User user = UserFixture.USER_ONE.생성();
        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_TWO.생성(user, walker);
        given(userRepository.findByEmail(walker.getEmail())).willReturn(Optional.of(walker));
        given(walkerReserveRepository.findByIdAndStatus(reserve.getId(), WalkerServiceStatus.WALKER_CHECKING)).willReturn(Optional.of(reserve));

        //when
        WalkerServiceStatus response = reserveService.changeStatus(authUser, reserve.getId(), WalkerServiceStatus.WALKER_ACCEPT);

        //then
        Assertions.assertThat(response.name()).isEqualTo(WalkerServiceStatus.WALKER_ACCEPT.name());
    }

    @Test
    void cancel() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        User walker = UserFixture.WALKER_ONE.생성(authUser);
        User user = UserFixture.USER_ONE.생성();
        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_ONE.생성(user, walker);
        PayHistory payHistory = PayHistory.builder()
                .reserve(reserve)
                .customer(user)
                .price(reserve.getPrice())
                .method("CARD")
                .id(1L)
                .build();

        given(userRepository.findByEmail(walker.getEmail())).willReturn(Optional.of(walker));
        given(walkerReserveRepository.findByIdAndStatus(reserve.getId(), WalkerServiceStatus.WALKER_ACCEPT)).willReturn(Optional.of(reserve));
        given(payHistoryRepository.findByReserve(reserve)).willReturn(Optional.of(payHistory));
        //when
        WalkerServiceStatus response = reserveService.cancel(authUser, reserve.getId());

        //then
        Assertions.assertThat(response.name()).isEqualTo(WalkerServiceStatus.WALKER_CANCEL.name());
    }

}