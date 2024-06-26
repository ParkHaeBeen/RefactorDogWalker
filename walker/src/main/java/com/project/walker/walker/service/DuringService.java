package com.project.walker.walker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.core.common.service.LocationUtil;
import com.project.core.common.service.redis.RedisService;
import com.project.core.common.token.AuthUser;
import com.project.core.domain.reserve.WalkerReserve;
import com.project.core.domain.reserve.WalkerServiceStatus;
import com.project.core.domain.user.User;
import com.project.core.domain.walkerservice.WalkerServiceRoute;
import com.project.walker.exception.DuringException;
import com.project.walker.exception.ErrorCode;
import com.project.walker.exception.ReserveException;
import com.project.walker.exception.user.UserException;
import com.project.walker.kafka.KafkaSender;
import com.project.walker.kafka.Topic;
import com.project.walker.kafka.dto.NoticeDto;
import com.project.walker.repository.UserRepository;
import com.project.walker.repository.WalkerReserveRepository;
import com.project.walker.repository.WalkerServiceRouteRepository;
import com.project.walker.walker.dto.request.DuringWalkerLocationRequest;
import com.project.walker.walker.dto.response.DuringWalkerEndResponse;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.project.walker.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class DuringService {

    private final RedisService redisService;
    private final WalkerReserveRepository walkerReserveRepository;
    private final UserRepository userRepository;
    private final WalkerServiceRouteRepository walkerServiceRouteRepository;
    private final KafkaSender kafkaSender;
    private final String starPrefix="start-";
    private final String start = "ON";
    private final String proceedPrefix = "proceed-";

    public boolean start(final AuthUser user, final Long id) {
        final User walker = validateWalker(user);

        if(isStarted(id, walker.getId())) {
            throw new DuringException(ErrorCode.RESERVE_PROCESS);
        }

        final WalkerReserve reserve = walkerReserveRepository
                .findByIdAndWalkerAndStatus(id, walker, WalkerServiceStatus.WALKER_ACCEPT)
                .orElseThrow(() -> new ReserveException(NOT_EXIST_RESERVE));

        final Duration diff = Duration.between(LocalDateTime.now().toLocalTime(), reserve.getDate().toLocalTime());

        if(diff.toMinutes() <= 60) {
            redisService.addData(generateKey(starPrefix, reserve.getId(), walker.getId()), start, reserve.getTimeUnit());
            return true;
        }

        return false;
    }

    public boolean checkStart(final AuthUser user, final Long id) {
        final User walker = validateWalker(user);

        return isStarted(id, walker.getId());
    }

    private boolean isStarted(final Long reserveId, final Long walkerId) {
        final Object isStarted = redisService.getData(generateKey(starPrefix, reserveId, walkerId));

        if(isStarted != null) {
            return isStarted.toString().trim().equals(start);
        }

        return false;
    }

    @Transactional
    public void addLocation(final AuthUser user, final DuringWalkerLocationRequest request) {
        final User walker = validateWalker(user);
        final WalkerReserve reserve = walkerReserveRepository.findByIdAndWalkerAndStatus(request.id(), walker, WalkerServiceStatus.WALKER_ACCEPT)
                .orElseThrow(() -> new ReserveException(NOT_EXIST_RESERVE));

        final Point point = LocationUtil.createPoint(request.lat(), request.lnt());
        redisService.addList(generateKey(proceedPrefix, reserve.getId(), walker.getId()), point);
    }

    public void notice(final AuthUser user, final Long id) {
        final User walker = validateWalker(user);
        final WalkerReserve reserve = walkerReserveRepository.findByIdAndWalkerAndStatus(id, walker, WalkerServiceStatus.WALKER_ACCEPT)
                .orElseThrow(() -> new ReserveException(NOT_EXIST_RESERVE));

        sendKafka(reserve, walker);
    }

    @Transactional
    public DuringWalkerEndResponse end(final AuthUser user, final Long id) {
        final User walker = validateWalker(user);
        final WalkerReserve reserve = walkerReserveRepository.findByIdAndWalkerAndStatus(id, walker, WalkerServiceStatus.WALKER_ACCEPT)
                .orElseThrow(() -> new ReserveException(NOT_EXIST_RESERVE));

        final List<Point> routes = redisService.getList(generateKey(proceedPrefix, reserve.getId(), walker.getId()));

        if(routes.isEmpty()) {
            //TODO: 없으면 어쩌지..?
        }

        final MultiPoint lineString = LocationUtil.createLineString(routes);
        walkerServiceRouteRepository.save(
                reserve.getId(),
                lineString.toString()
        );

        reserve.changeStatus(WalkerServiceStatus.FINISH);
        final WalkerServiceRoute route = walkerServiceRouteRepository.findByReserve(reserve)
                .orElseThrow(() -> new DuringException(NOT_FOUND_ROUTE));

        return DuringWalkerEndResponse.toResponse(route, LocationUtil.createLocation(lineString.toString()));
    }

    private void sendKafka(final WalkerReserve reserve, final User walker) {
        try {
            kafkaSender.sendNotice(Topic.NOTICE, NoticeDto.builder()
                            .reserveId(reserve.getId())
                            .walkerId(walker.getId())
                    .build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private User validateWalker(final AuthUser user) {
        return userRepository.findByEmailAndRole(user.email() , user.role())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));
    }

    private String generateKey(final String prefix, final Long id, final Long walkerId) {
        return prefix + id+" : "+walkerId;
    }
}
