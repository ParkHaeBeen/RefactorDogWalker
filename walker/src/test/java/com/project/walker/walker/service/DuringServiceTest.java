package com.project.walker.walker.service;

import com.project.core.common.service.LocationUtil;
import com.project.core.common.service.redis.RedisService;
import com.project.core.common.token.AuthUser;
import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.reserve.WalkerServiceStatus;
import com.project.core.domain.user.User;
import com.project.core.domain.walkerservice.WalkerServiceRoute;
import com.project.walker.fixture.AuthUserFixture;
import com.project.walker.fixture.UserFixture;
import com.project.walker.fixture.WalkerReserveFixture;
import com.project.walker.kafka.KafkaSender;
import com.project.walker.repository.UserRepository;
import com.project.walker.repository.WalkerReserveRepository;
import com.project.walker.repository.WalkerServiceRouteRepository;
import com.project.walker.walker.dto.request.DuringWalkerLocationRequest;
import com.project.walker.walker.dto.response.DuringWalkerEndResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DuringServiceTest {

    @Mock
    private RedisService redisService;

    @Mock
    private WalkerReserveRepository walkerReserveRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalkerServiceRouteRepository walkerServiceRouteRepository;

    @Mock
    private KafkaSender kafkaSender;

    @InjectMocks
    private DuringService duringService;

    @Test
    void start() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        User walker = UserFixture.WALKER_ONE.생성(authUser);
        User user = UserFixture.USER_ONE.생성();
        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_TWO.생성(user, walker);

        given(userRepository.findByEmailAndRole(walker.getEmail(), walker.getRole())).willReturn(Optional.of(walker));
        given(redisService.getData(anyString())).willReturn(null);
        given(walkerReserveRepository.findByIdAndWalkerAndStatus(reserve.getId(), walker, WalkerServiceStatus.WALKER_ACCEPT)).willReturn(Optional.of(reserve));

        //when
        boolean response = duringService.start(authUser, reserve.getId());

        //then
        Assertions.assertThat(response).isTrue();
    }

    @Test
    void checkStart() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        User walker = UserFixture.WALKER_ONE.생성(authUser);
        User user = UserFixture.USER_ONE.생성();
        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_TWO.생성(user, walker);
        given(userRepository.findByEmailAndRole(walker.getEmail(), walker.getRole())).willReturn(Optional.of(walker));
        given(redisService.getData(anyString())).willReturn("ON");

        //when
        boolean response = duringService.checkStart(authUser, reserve.getId());

        //then
        Assertions.assertThat(response).isTrue();
    }

    @Test
    void addLocation() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        User walker = UserFixture.WALKER_ONE.생성(authUser);
        User user = UserFixture.USER_ONE.생성();
        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_TWO.생성(user, walker);
        DuringWalkerLocationRequest request = new DuringWalkerLocationRequest(reserve.getId(), 12.30303, 132.333);

        given(userRepository.findByEmailAndRole(walker.getEmail(), walker.getRole())).willReturn(Optional.of(walker));
        given(walkerReserveRepository.findByIdAndWalkerAndStatus(reserve.getId(), walker, WalkerServiceStatus.WALKER_ACCEPT)).willReturn(Optional.of(reserve));

        //when
        //then
        duringService.addLocation(authUser, request);
    }

    @Test
    void notice() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        User walker = UserFixture.WALKER_ONE.생성(authUser);
        User user = UserFixture.USER_ONE.생성();
        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_TWO.생성(user, walker);

        given(userRepository.findByEmailAndRole(walker.getEmail(), walker.getRole())).willReturn(Optional.of(walker));
        given(walkerReserveRepository.findByIdAndWalkerAndStatus(reserve.getId(), walker, WalkerServiceStatus.WALKER_ACCEPT)).willReturn(Optional.of(reserve));

        //when
        //then
        duringService.notice(authUser, reserve.getId());
    }

    @Test
    void end() {
        //given
        AuthUser authUser = AuthUserFixture.AUTH_USER_ONE.생성();
        User walker = UserFixture.WALKER_ONE.생성(authUser);
        User user = UserFixture.USER_ONE.생성();
        Point point = LocationUtil.createPoint(12.333, 132.33333);
        MultiPoint lineString = LocationUtil.createLineString(List.of(point));
        WalkerReserve reserve = WalkerReserveFixture.WALKER_RESERVE_TWO.생성(user, walker);
        WalkerServiceRoute route = WalkerServiceRoute.builder()
                .reserve(reserve)
                .routes(lineString)
                .createdAt(LocalDateTime.now())
                .build();

        given(userRepository.findByEmailAndRole(walker.getEmail(), walker.getRole())).willReturn(Optional.of(walker));
        given(walkerReserveRepository.findByIdAndWalkerAndStatus(reserve.getId(), walker, WalkerServiceStatus.WALKER_ACCEPT)).willReturn(Optional.of(reserve));
        given(redisService.getList(anyString())).willReturn(List.of(point));
        given(walkerServiceRouteRepository.save(reserve.getId(), lineString.toString())).willReturn(1);
        given(walkerServiceRouteRepository.findByReserve(reserve)).willReturn(Optional.of(route));

        //when
        DuringWalkerEndResponse response = duringService.end(authUser, reserve.getId());

        //then
        Assertions.assertThat(response.routes()).isEqualTo(LocationUtil.createLocation(lineString.toString()));
    }
}