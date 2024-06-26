package com.project.walker.user.service.service;

import com.project.core.common.oauth.GoogleOauth;
import com.project.core.common.oauth.dto.GoogleResponse;
import com.project.core.common.token.RefreshTokenService;
import com.project.core.common.token.TokenProvider;
import com.project.core.domain.user.User;
import com.project.walker.exception.user.UserException;
import com.project.walker.user.dto.request.UserJoinPriceRequest;
import com.project.walker.user.dto.request.UserJoinRequest;
import com.project.walker.user.dto.response.UserJoinResponse;
import com.project.walker.user.dto.response.UserLoginResponse;
import com.project.walker.user.dto.response.UserTokenResponse;
import com.project.walker.repository.UserRepository;
import com.project.walker.repository.WalkerPriceRepository;
import com.project.walker.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.project.walker.fixture.UserFixture;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private GoogleOauth googleOauth;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalkerPriceRepository walkerPriceRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getLoginView() {
        //given
        String loginView = "http://google";
        given(googleOauth.getLoginView()).willReturn(loginView);

        //when
        String response = userService.getLoginView();

        //then
        Assertions.assertThat(response).isEqualTo(loginView);
    }

    @Test
    void login_success() {
        //given
        String code = "googleCode";
        User user = UserFixture.USER_ONE.생성();
        GoogleResponse googleResponse = GoogleResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        given(googleOauth.login(code)).willReturn(googleResponse);
        given(userRepository.findByEmail(googleResponse.getEmail())).willReturn(Optional.of(user));
        given(tokenProvider.generateAccessToken(user.getEmail(), user.getRole())).willReturn(accessToken);
        given(refreshTokenService.generateToken(user.getEmail())).willReturn(refreshToken);

        //when
        UserLoginResponse response = userService.login(code);

        //then
        Assertions.assertThat(response.email()).isEqualTo(user.getEmail());
        Assertions.assertThat(response.name()).isEqualTo(user.getName());
        Assertions.assertThat(response.accessToken()).isEqualTo(accessToken);
        Assertions.assertThat(response.refreshToken()).isEqualTo(refreshToken);
    }

    @Test
    void login_fail_not_exist_member() {
        //given
        String code = "googleCode";
        GoogleResponse googleResponse = GoogleResponse.builder()
                .email("email")
                .name("name")
                .build();

        given(googleOauth.login(code)).willReturn(googleResponse);
        given(userRepository.findByEmail(googleResponse.getEmail())).willReturn(Optional.empty());

        //when
        //then
        Assertions.assertThatThrownBy(() -> userService.login(code))
                .isExactlyInstanceOf(UserException.class);
    }

    @Test
    void join_success() {
        //given
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        User user = UserFixture.USER_ONE.생성();
        UserJoinPriceRequest priceRequest1 = new UserJoinPriceRequest(30, 10000);
        UserJoinPriceRequest priceRequest2 = new UserJoinPriceRequest(40, 15000);
        UserJoinRequest request = new UserJoinRequest(
                user.getPhoneNumber(),
                user.getLocation().getX(),
                user.getLocation().getY(),
                user.getName(),
                "accessToken",
                List.of(),
                List.of(priceRequest1, priceRequest2),
                "email"
        );

        GoogleResponse googleResponse = GoogleResponse.builder()
                .email("email")
                .name(request.name())
                .build();


        given(googleOauth.getUserInfo(request.token())).willReturn(googleResponse);
        given(userRepository.save(any())).willReturn(user);
        given(tokenProvider.generateAccessToken(user.getEmail(), user.getRole())).willReturn(accessToken);
        given(refreshTokenService.generateToken(anyString())).willReturn(refreshToken);

        //when
         UserJoinResponse response = userService.join(request);

        //then
        Assertions.assertThat(response.email()).isEqualTo(user.getEmail());
        Assertions.assertThat(response.name()).isEqualTo(user.getName());
        Assertions.assertThat(response.accessToken()).isEqualTo(accessToken);
        Assertions.assertThat(response.refreshToken()).isEqualTo(refreshToken);
    }

    @Test
    void generateAccessToken_success() {
        //given
        User user = UserFixture.USER_ONE.생성();
        String refreshToken = "refreshToken";
        String email = "email";

        String newAccessToken = "newAccessToken";
        String newRefreshToken = "newRefreshToken";

        given(refreshTokenService.getTokenInfo(refreshToken, "email")).willReturn(email);
        given(refreshTokenService.validateToken(email)).willReturn(false);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(tokenProvider.generateAccessToken(user.getEmail(), user.getRole())).willReturn(newAccessToken);
        given(refreshTokenService.generateToken(email)).willReturn(newRefreshToken);

        //when
        UserTokenResponse response = userService.generateAccessToken(refreshToken);

        //then
        Assertions.assertThat(response.accessToken()).isEqualTo(newAccessToken);
        Assertions.assertThat(response.refreshToken()).isEqualTo(newRefreshToken);
    }

    @Test
    void generateAccessToken_fail_refresh_token_expired() {
        //given
        String refreshToken = "refreshToken";
        String email = "email";

        given(refreshTokenService.getTokenInfo(refreshToken, "email")).willReturn(email);
        given(refreshTokenService.validateToken(email)).willReturn(true);

        //when
        //then
        Assertions.assertThatThrownBy(() -> userService.generateAccessToken(refreshToken))
                .isExactlyInstanceOf(UserException.class);
    }

    @Test
    void generateAccessToken_fail_not_exit_member() {
        //given
        String refreshToken = "refreshToken";
        String email = "email";

        given(refreshTokenService.getTokenInfo(refreshToken, "email")).willReturn(email);
        given(refreshTokenService.validateToken(email)).willReturn(false);
        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        //when
        //then
        Assertions.assertThatThrownBy(() -> userService.generateAccessToken(refreshToken))
                .isExactlyInstanceOf(UserException.class);
    }
}