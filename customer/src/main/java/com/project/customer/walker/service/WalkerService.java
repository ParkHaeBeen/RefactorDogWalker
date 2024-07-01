package com.project.customer.walker.service;

import com.project.core.common.service.LocationUtil;
import com.project.core.common.service.dto.Location;
import com.project.core.common.service.redis.RedisService;
import com.project.core.common.token.AuthUser;
import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.user.User;
import com.project.core.domain.user.customer.CustomerDog;
import com.project.core.domain.walkerservice.WalkerServiceRoute;
import com.project.customer.exception.DuringException;
import com.project.customer.exception.ErrorCode;
import com.project.customer.exception.ReserveException;
import com.project.customer.exception.user.UserException;
import com.project.customer.repository.CustomerDogRepository;
import com.project.customer.repository.UserRepository;
import com.project.customer.repository.WalkerReserveRepository;
import com.project.customer.repository.WalkerServiceRouteRepository;
import com.project.customer.walker.dto.response.WalkerInfoResponse;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.project.customer.exception.ErrorCode.NOT_EXIST_MEMBER;
import static com.project.customer.exception.ErrorCode.NOT_EXIST_RESERVE;

@Service
@RequiredArgsConstructor
public class WalkerService {
    private final RedisService redisService;
    private final UserRepository userRepository;
    private final WalkerReserveRepository walkerReserveRepository;
    private final WalkerServiceRouteRepository walkerServiceRouteRepository;
    private final CustomerDogRepository customerDogRepository;
    private final String starPrefix="start-";
    private final String start = "ON";
    private final String proceedPrefix = "proceed-";

    public boolean checkStart(final AuthUser user, final Long id) {
        final User customer = userRepository.findByEmailAndRole(user.email(), user.role())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));

        final WalkerReserve reserve = walkerReserveRepository.findByCustomerAndId(customer, id)
                .orElseThrow(() -> new ReserveException(NOT_EXIST_RESERVE));

        return isStarted(reserve.getId(), reserve.getWalker().getId());
    }

    private boolean isStarted(final Long reserveId, final Long walkerId) {
        final Object isStarted = redisService.getData(generateKey(starPrefix, reserveId, walkerId));
        if(isStarted != null) {
            return isStarted.toString().trim().equals(start);
        }

        return false;
    }

    public WalkerInfoResponse read(final AuthUser user, final Long id) {
        final User customer = userRepository.findByEmailAndRole(user.email(), user.role())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));

        final WalkerReserve reserve = walkerReserveRepository.findByCustomerAndId(customer, id)
                .orElseThrow(() -> new ReserveException(NOT_EXIST_RESERVE));
        final CustomerDog customerDog = customerDogRepository.findByUserId(customer.getId())
                .orElseThrow(() -> new UserException(ErrorCode.NOT_EXIST_DOG));

        return WalkerInfoResponse.builder()
                .walkerName(reserve.getWalker().getName())
                .dogName(customerDog.getName())
                .date(reserve.getDate())
                .build();
    }

    public List<Location> readNowLocation(final AuthUser user, final Long id) {
        final User customer = userRepository.findByEmailAndRole(user.email(), user.role())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));

        final WalkerReserve reserve = walkerReserveRepository.findByCustomerAndId(customer, id)
                .orElseThrow(() -> new ReserveException(NOT_EXIST_RESERVE));

        final List<Point> routes = redisService.getList(generateKey(proceedPrefix, reserve.getId(), reserve.getWalker().getId()));

        return LocationUtil.createLocation(LocationUtil.createLineString(routes).toString());
    }

    private String generateKey(final String prefix, final Long id, final Long walkerId) {
        return prefix + id+" : "+walkerId;
    }

    public List<Location> readLocation(final AuthUser user, final Long id) {
        final User customer = userRepository.findByEmailAndRole(user.email(), user.role())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));

        final WalkerReserve reserve = walkerReserveRepository.findByCustomerAndId(customer, id)
                .orElseThrow(() -> new ReserveException(NOT_EXIST_RESERVE));

        final WalkerServiceRoute route = walkerServiceRouteRepository.findByReserve(reserve)
                .orElseThrow(() -> new DuringException(ErrorCode.NOT_FOUND_ROUTE));
        return LocationUtil.createLocation(route.getRoutes().toString());
    }
}
