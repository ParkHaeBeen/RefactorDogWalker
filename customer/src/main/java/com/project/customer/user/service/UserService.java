package com.project.customer.user.service;

import com.project.core.common.oauth.GoogleOauth;
import com.project.core.common.oauth.dto.GoogleResponse;
import com.project.core.common.token.TokenProvider;
import com.project.core.domain.user.Role;
import com.project.core.domain.user.User;
import com.project.core.domain.user.customer.CustomerDog;
import com.project.customer.common.service.LocationUtil;
import com.project.customer.exception.user.UserException;
import com.project.customer.user.dto.response.UserJoinRequest;
import com.project.customer.user.dto.response.UserJoinResponse;
import com.project.customer.user.dto.response.UserLoginResponse;
import com.project.customer.user.dto.response.UserTokenResponse;
import com.project.customer.user.repository.CustomerDogRepository;
import com.project.customer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.project.customer.exception.ErrorCode.NOT_EXIST_MEMBER;
import static com.project.customer.exception.ErrorCode.TOKEN_EXPIRED;

@RequiredArgsConstructor
@Service
public class UserService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final GoogleOauth oauth;
    private final UserRepository userRepository;
    private final CustomerDogRepository customerDogRepository;
    private final ImgService imgService;

    public String getLoginView( ){
        return oauth.getLoginView();
    }

    @Transactional
    public UserLoginResponse login(final String code) {
        final GoogleResponse response = oauth.login(code);
        final User user = userRepository.findByEmail(response.getEmail()).orElseThrow(
                () -> new UserException(NOT_EXIST_MEMBER, response.getIdToken())
        );

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
    public UserJoinResponse join(final UserJoinRequest request , final MultipartFile dogImg) {
        final GoogleResponse user = oauth.getUserInfo(request.token());

        final User newUser = userRepository.save(
                User.builder()
                .email(user.getEmail())
                .name(user.getName())
                .location(LocationUtil.createPoint(request.lat(), request.lnt()))
                .phoneNumber(request.phoneNumber())
                .role(Role.USER)
                .build()
        );

        final String imgUrl = imgService.save(dogImg);

        customerDogRepository.save(CustomerDog.builder()
                .userId(newUser.getId())
                .imgUrl(imgUrl)
                .birth(request.dogBirth())
                .name(request.dogName())
                .type(request.dogType())
                .description(request.dogDescription())
                .build());

        final String accessToken = tokenProvider.generateAccessToken(newUser.getEmail(),newUser.getRole());
        final String refreshToken = refreshTokenService.generateToken(user.getEmail());

        return UserJoinResponse.builder()
                .name(newUser.getName())
                .email(newUser.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
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
