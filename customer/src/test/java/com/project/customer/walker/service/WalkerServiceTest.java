package com.project.customer.walker.service;

import com.project.core.common.service.LocationUtil;
import com.project.core.common.service.dto.Location;
import com.project.core.common.service.redis.RedisService;
import com.project.core.common.token.AuthUser;
import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.reserve.WalkerServiceStatus;
import com.project.core.domain.user.User;
import com.project.core.domain.user.customer.CustomerDog;
import com.project.core.domain.walkerservice.WalkerServiceRoute;
import com.project.customer.fixture.AuthUserFixture;
import com.project.customer.fixture.UserFixture;
import com.project.customer.fixture.WalkerReserveFixture;
import com.project.customer.repository.CustomerDogRepository;
import com.project.customer.repository.UserRepository;
import com.project.customer.repository.WalkerReserveRepository;
import com.project.customer.repository.WalkerServiceRouteRepository;
import com.project.customer.walker.dto.response.WalkerInfoResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.project.customer.fixture.AuthUserFixture.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WalkerServiceTest {

    @Mock
    private RedisService redisService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalkerReserveRepository walkerReserveRepository;

    @Mock
    private WalkerServiceRouteRepository walkerServiceRouteRepository;

    @Mock
    private CustomerDogRepository customerDogRepository;

    @InjectMocks
    private WalkerService walkerService;

    @Test
    void checkStart() {
        //given
        AuthUser authUser = AUTH_USER_ONE.생성();
        User walker = UserFixture.WALKER_ONE.생성(authUser);
        User user = UserFixture.USER_ONE.생성();
        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_TWO.생성(user, walker);

        given(userRepository.findByEmailAndRole(walker.getEmail(), walker.getRole())).willReturn(Optional.of(user));
        given(walkerReserveRepository.findByCustomerAndId(user, reserve.getId())).willReturn(Optional.of(reserve));
        given(redisService.getData(anyString())).willReturn("ON");

        //when
        boolean response = walkerService.checkStart(authUser, reserve.getId());

        //then
        Assertions.assertThat(response).isTrue();
    }

    @Test
    void read() {
        //given
        AuthUser authUser = AUTH_USER_ONE.생성();
        User walker = UserFixture.WALKER_ONE.생성(authUser);
        User user = UserFixture.USER_ONE.생성();
        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_TWO.생성(user, walker);

        CustomerDog customerDog = CustomerDog.builder()
                .birth(LocalDate.now())
                .userId(user.getId())
                .imgUrl("img")
                .type("maltiz")
                .description("cute")
                .build();

        given(userRepository.findByEmailAndRole(walker.getEmail(), walker.getRole())).willReturn(Optional.of(user));
        given(walkerReserveRepository.findByCustomerAndId(user, reserve.getId())).willReturn(Optional.of(reserve));
        given(customerDogRepository.findByUserId(user.getId())).willReturn(Optional.of(customerDog));

        //when
        WalkerInfoResponse response = walkerService.read(authUser, reserve.getId());

        //then
        Assertions.assertThat(response.walkerName()).isEqualTo(walker.getName());
        Assertions.assertThat(response.dogName()).isEqualTo(customerDog.getName());
    }

    @Test
    void readNowLocation() {
        //given
        AuthUser authUser = AUTH_USER_ONE.생성();
        User walker = UserFixture.WALKER_ONE.생성(authUser);
        User user = UserFixture.USER_ONE.생성();
        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_TWO.생성(user, walker);
        Point point = LocationUtil.createPoint(12.344444, 132.44444);

        given(userRepository.findByEmailAndRole(walker.getEmail(), walker.getRole())).willReturn(Optional.of(user));
        given(walkerReserveRepository.findByCustomerAndId(user, reserve.getId())).willReturn(Optional.of(reserve));
        given(redisService.getList(anyString())).willReturn(List.of(point));

        //when
        List<Location> response = walkerService.readNowLocation(authUser, reserve.getId());

        //then
        Assertions.assertThat(response.getFirst().lat()).isEqualTo(point.getX());
        Assertions.assertThat(response.getFirst().lnt()).isEqualTo(point.getY());
    }

    @Test
    void readLocation() {
        //given
        AuthUser authUser = AUTH_USER_ONE.생성();
        User walker = UserFixture.WALKER_ONE.생성(authUser);
        User user = UserFixture.USER_ONE.생성();
        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_TWO.생성(user, walker);
        Point point = LocationUtil.createPoint(12.344444, 132.44444);
        MultiPoint lineString = LocationUtil.createLineString(List.of(point));

        WalkerServiceRoute route = WalkerServiceRoute.builder()
                .reserve(reserve)
                .routes(lineString)
                .createdAt(LocalDateTime.now())
                .build();


        given(userRepository.findByEmailAndRole(walker.getEmail(), walker.getRole())).willReturn(Optional.of(user));
        given(walkerReserveRepository.findByCustomerAndId(user, reserve.getId())).willReturn(Optional.of(reserve));
        given(walkerServiceRouteRepository.findByReserve(reserve)).willReturn(Optional.of(route));

        //when
        List<Location> response = walkerService.readLocation(authUser, reserve.getId());

        //then
        Assertions.assertThat(response.getFirst().lat()).isEqualTo(point.getX());
        Assertions.assertThat(response.getFirst().lnt()).isEqualTo(point.getY());
    }

}