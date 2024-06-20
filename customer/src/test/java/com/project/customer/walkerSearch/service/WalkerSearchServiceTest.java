package com.project.customer.walkerSearch.service;

import com.project.core.common.token.AuthUser;
import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.reserve.WalkerServiceStatus;
import com.project.core.domain.user.User;
import com.project.customer.fixture.AuthUserFixture;
import com.project.customer.fixture.UserFixture;
import com.project.customer.fixture.WalkerReserveFixture;
import com.project.customer.user.repository.UserRepository;
import com.project.customer.walkerSearch.dto.request.WalkerReserveRequest;
import com.project.customer.walkerSearch.dto.request.WalkerSearchRequest;
import com.project.customer.walkerSearch.dto.response.WalkerReserveResponse;
import com.project.customer.walkerSearch.dto.response.WalkerSearchResponse;
import com.project.customer.walkerSearch.repository.WalkerPriceRepository;
import com.project.customer.walkerSearch.repository.WalkerReserveRepository;
import com.project.customer.walkerSearch.repository.WalkerSchedulePermRepository;
import com.project.customer.walkerSearch.repository.WalkerScheduleTempRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class WalkerSearchServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalkerSchedulePermRepository walkerSchedulePermRepository;

    @Mock
    private WalkerScheduleTempRepository walkerScheduleTempRepository;

    @Mock
    private WalkerPriceRepository walkerPriceRepository;

    @Mock
    private WalkerReserveRepository walkerReserveRepository;

    @InjectMocks
    private WalkerSearchService walkerSearchService;

    @Test
    void readAll_request_name_not_null() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        WalkerSearchRequest request = new WalkerSearchRequest("name", 12.0, 12.0, 3);
        User user = UserFixture.USER_ONE.생성(authUser);

        User walker1 = UserFixture.WALKER_ONE.생성();
        User walker2 = UserFixture.WALKER_TWO.생성();
        Page<User> walkers = new PageImpl<>(Arrays.asList(walker1, walker2));

        given(userRepository.findByEmailAndRole(authUser.email(), authUser.role())).willReturn(Optional.of(user));
        given(userRepository.findAllWithCircleAreaAndName(user.getLocation(), request.radius(), request.name(), Pageable.unpaged()))
                .willReturn(walkers);

        //when
        List<WalkerSearchResponse> response = walkerSearchService.readAll(authUser, request, Pageable.unpaged());

        //then
        Assertions.assertThat(response.size()).isEqualTo(2);
    }

    @Test
    void readAll_request_name_null() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        WalkerSearchRequest request = new WalkerSearchRequest("", 12.0, 12.0, 3);
        User user = UserFixture.USER_ONE.생성(authUser);

        User walker1 = UserFixture.WALKER_ONE.생성();
        User walker2 = UserFixture.WALKER_TWO.생성();
        Page<User> walkers = new PageImpl<>(Arrays.asList(walker1, walker2));

        given(userRepository.findByEmailAndRole(authUser.email(), authUser.role())).willReturn(Optional.of(user));
        given(userRepository.findAllWithCircleArea(user.getLocation(), request.radius(), Pageable.unpaged()))
                .willReturn(walkers);

        //when
        List<WalkerSearchResponse> response = walkerSearchService.readAll(authUser, request, Pageable.unpaged());

        //then
        Assertions.assertThat(response.size()).isEqualTo(2);
    }

    @Test
    void read() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        User user = UserFixture.USER_ONE.생성(authUser);
        User walker = UserFixture.WALKER_ONE.생성();

        given(userRepository.findByEmailAndRole(authUser.email(), authUser.role())).willReturn(Optional.of(user));
        given(userRepository.findById(walker.getId())).willReturn(Optional.of(walker));

        //when

        //then
    }

    @Test
    void readReserve() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        User user = UserFixture.USER_ONE.생성(authUser);
        WalkerReserveRequest request = new WalkerReserveRequest(3L, LocalDate.now());
        User walker = UserFixture.WALKER_ONE.생성();
        WalkerReserve reserve1 = WalkerReserveFixture.WALKER_RESERVE_ONE.생성(user, walker);

        given(userRepository.findByEmailAndRole(authUser.email(), authUser.role())).willReturn(Optional.of(user));
        given(userRepository.findById(request.id())).willReturn(Optional.of(walker));
        given(walkerReserveRepository.findByWalkerAndStatusAndDateBetween(walker, WalkerServiceStatus.WALKER_ACCEPT, request.date().atStartOfDay(), request.date().atTime(LocalTime.MAX)))
                .willReturn(List.of(reserve1));
        //when
        List<WalkerReserveResponse> response = walkerSearchService.readReserve(authUser, request);

        //then
        Assertions.assertThat(response.size()).isEqualTo(1);
    }
}