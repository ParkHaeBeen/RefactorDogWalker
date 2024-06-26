package com.project.walker.user.service;

import com.project.core.common.oauth.GoogleOauth;
import com.project.core.common.oauth.dto.GoogleResponse;
import com.project.core.common.service.LocationUtil;
import com.project.core.common.token.RefreshTokenService;
import com.project.core.common.token.TokenProvider;
import com.project.core.domain.user.Role;
import com.project.core.domain.user.User;
import com.project.core.domain.user.walker.WalkerPrice;
import com.project.core.domain.user.walker.WalkerSchedulePerm;
import com.project.walker.exception.ErrorCode;
import com.project.walker.exception.user.UserException;
import com.project.walker.user.dto.request.UserJoinRequest;
import com.project.walker.user.dto.response.UserJoinResponse;
import com.project.walker.user.dto.response.UserLoginResponse;
import com.project.walker.user.dto.response.UserTokenResponse;
import com.project.walker.repository.UserRepository;
import com.project.walker.repository.WalkerPriceRepository;
import com.project.walker.repository.WalkerSchedulePermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.walker.exception.ErrorCode.NOT_EXIST_MEMBER;
import static com.project.walker.exception.ErrorCode.TOKEN_EXPIRED;

@RequiredArgsConstructor
@Service
public class UserService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final GoogleOauth oauth;
    private final UserRepository userRepository;
    private final WalkerSchedulePermRepository walkerSchedulePermRepository;
    private final WalkerPriceRepository walkerPriceRepository;

    public String getLoginView( ){
        return oauth.getLoginView();
    }

    @Transactional
    public UserLoginResponse login(final String code) {
        GoogleResponse response;
        //TEMP : Oauth 없이 로그인할 수 있게
        if(!code.contains("gmail.com")) {
            response = oauth.login(code);
        } else {
            response = new GoogleResponse(code, "name", "token");
        }

        final User user = userRepository.findByEmail(response.getEmail())
                .orElseThrow(() -> new UserException(NOT_EXIST_MEMBER, response.getIdToken()));

        final String accessToken = tokenProvider.generateAccessToken(user.getEmail(), user.getRole());
        final String refreshToken = refreshTokenService.generateToken(user.getEmail());

        return UserLoginResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public UserJoinResponse join(final UserJoinRequest request) {
        // TEMP: Oauth 없이 회원가입 할 수 있게
        GoogleResponse user;
        if(request.token().equals(" ")) {
            user = new GoogleResponse(request.email(), request.name(), "token");
        } else {
            user = oauth.getUserInfo(request.token());
        }

        final User newUser = userRepository.save(
                User.builder()
                .email(user.getEmail())
                .name(user.getName())
                .location(LocationUtil.createPoint(request.lat(), request.lnt()))
                .phoneNumber(request.phoneNumber())
                .role(Role.WALKER)
                .build()
        );

        saveDetail(request, newUser.getId());

        final String accessToken = tokenProvider.generateAccessToken(newUser.getEmail(),newUser.getRole());
        final String refreshToken = refreshTokenService.generateToken(user.getEmail());

        return UserJoinResponse.builder()
                .name(newUser.getName())
                .email(newUser.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveDetail(final UserJoinRequest request, final Long id) {
        if(!request.schedule().isEmpty()) {
            final List<WalkerSchedulePerm> schedules = request.schedule().stream()
                    .map(schedule ->
                            WalkerSchedulePerm.builder()
                                    .walkerId(id)
                                    .dayOfWeek(schedule.dayOfWeek())
                                    .startTime(schedule.startTime())
                                    .endTime(schedule.endTime())
                                    .build()
                    ).collect(Collectors.toList());

            walkerSchedulePermRepository.saveAll(schedules);
        }

        if(!request.price().isEmpty()) {
            final List<WalkerPrice> prices = request.price().stream()
                    .map(price ->
                            WalkerPrice.builder()
                                    .walkerId(id)
                                    .price(price.price())
                                    .timeUnit(price.timeUnit())
                                    .build()
                    ).collect(Collectors.toList());

            walkerPriceRepository.saveAll(prices);
        } else {
            throw new UserException(ErrorCode.NOT_WRITE_SERVICE_PRICE);
        }
    }

    @Transactional
    public UserTokenResponse generateAccessToken(final String refreshToken) {
        final String email = refreshTokenService.getTokenInfo(refreshToken, "email");

        if(refreshTokenService.validateToken(email)) {
          throw new UserException(TOKEN_EXPIRED);
        }

        final User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserException(NOT_EXIST_MEMBER)
        );

        final String newAccessToken = tokenProvider.generateAccessToken(user.getEmail(), user.getRole());
        final String newRefreshToken = refreshTokenService.generateToken(email);

        return UserTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
