package com.project.customer.walkerSearch.service;

import com.project.core.common.token.AuthUser;
import com.project.core.domain.reserve.WalkerServiceStatus;
import com.project.core.domain.user.User;
import com.project.customer.exception.user.UserException;
import com.project.customer.user.repository.UserRepository;
import com.project.customer.walkerSearch.dto.request.WalkerReserveRequest;
import com.project.customer.walkerSearch.dto.request.WalkerSearchRequest;
import com.project.customer.walkerSearch.dto.response.*;
import com.project.customer.walkerSearch.repository.WalkerPriceRepository;
import com.project.customer.walkerSearch.repository.WalkerReserveRepository;
import com.project.customer.walkerSearch.repository.WalkerSchedulePermRepository;
import com.project.customer.walkerSearch.repository.WalkerScheduleTempRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static com.project.customer.exception.ErrorCode.NOT_EXIST_MEMBER;

@Service
@RequiredArgsConstructor
public class WalkerSearchService {

    private final UserRepository userRepository;
    private final WalkerSchedulePermRepository walkerSchedulePermRepository;
    private final WalkerScheduleTempRepository walkerScheduleTempRepository;
    private final WalkerPriceRepository walkerPriceRepository;
    private final WalkerReserveRepository walkerReserveRepository;

    public List<WalkerSearchResponse> readAll(
            final AuthUser user,
            final WalkerSearchRequest request,
            final Pageable pageable
    ) {
        final User customer = userRepository.findByEmailAndRole(user.email(), user.role())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));

        if(request.name().isBlank()) {
            return userRepository.findAllWithCircleArea(customer.getLocation(), request.radius(), pageable)
                    .stream()
                    .map(WalkerSearchResponse::toResponse)
                    .toList();
        }

        return userRepository.findAllWithCircleAreaAndName(customer.getLocation(), request.radius(), request.name(), pageable)
                .stream()
                .map(WalkerSearchResponse::toResponse)
                .toList();
    }

    public WalkerSearchDetailResponse read(final AuthUser user, final Long id) {
        final User walker = validateUser(user, id);

        // TODO: prices, schedulePrems, scheduleTemps를 join
        final List<WalkerPriceResponse> prices = walkerPriceRepository.findByWalkerId(walker.getId())
                .stream()
                .map(WalkerPriceResponse::toResponse)
                .toList();
        final List<WalkerPermUnAvailDateResponse> schedulePerms = walkerSchedulePermRepository.findByWalkerId(walker.getId())
                .stream()
                .map(WalkerPermUnAvailDateResponse::toResponse)
                .toList();
        final List<WalkerTempUnAvailDateResponse> scheduleTemps = walkerScheduleTempRepository.findByWalkerIdAndUnAvailAtGreaterThan(walker.getId(), LocalDate.now())
                .stream()
                .map(WalkerTempUnAvailDateResponse::toResponse)
                .toList();

        return WalkerSearchDetailResponse.builder()
                .id(walker.getId())
                .lat(walker.getLocation().getX())
                .lnt(walker.getLocation().getY())
                .name(walker.getName())
                .prices(prices)
                .permUnAvailDates(schedulePerms)
                .tempUnAvailDates(scheduleTemps)
                .build();
    }

    public List<WalkerReserveResponse> readReserve(final AuthUser user, final WalkerReserveRequest request) {
        final User walker = validateUser(user, request.id());

        return walkerReserveRepository.findByWalkerAndStatusAndDateBetween(
                        walker
                        , WalkerServiceStatus.WALKER_ACCEPT
                        , request.date().atStartOfDay()
                        , request.date().atTime(LocalTime.MAX))
                .stream()
                .map(WalkerReserveResponse::toResponse)
                .toList();
    }

    private User validateUser(AuthUser user, Long request) {
        userRepository.findByEmailAndRole(user.email(), user.role())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));

        return userRepository.findById(request)
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER));
    }
}
