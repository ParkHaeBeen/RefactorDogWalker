package com.project.customer.user.service;

import com.project.core.common.token.TokenProvider;
import com.project.customer.common.service.redis.RedisService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

    @Mock
    private RedisService redisService;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    private long REFRESH_TOKEN_EXPIRE_TIME;
    private static final String REDIS_REFRESH = "REDIS_REFRESH";

    @Test
    void generateToken() {
        //given
        String email = "email";
        String token = "refreshToken";
        given(tokenProvider.generateRefreshToken(email, REFRESH_TOKEN_EXPIRE_TIME)).willReturn(token);

        //when
        String response = refreshTokenService.generateToken(email);

        //then
        Assertions.assertThat(response).isEqualTo(token);
    }

    @Test
    void getTokenInfo() {
        //given
        String token = "refreshToken";
        String tokenInfo = "tokenInfo";
        given(tokenProvider.parseClaims(token).getBody().get("email", String.class)).willReturn(tokenInfo);

        //when
        String response = refreshTokenService.getTokenInfo(token);

        //then
        Assertions.assertThat(response).isEqualTo(tokenInfo);
    }

    @Test
    void validateToken_success() {
        //given
        String email = "email";
        String token = "refreshToken";

        given(redisService.getData(REDIS_REFRESH+email)).willReturn(token);
        given(tokenProvider.validateRefreshToken(token)).willReturn(false);
        given(tokenProvider.parseClaims(token).getBody().get("email", String.class)).willReturn(email);
        given(refreshTokenService.getTokenInfo(token)).willReturn(email);

        //when
        boolean response = refreshTokenService.validateToken(email);

        //then
        Assertions.assertThat(response).isTrue();
    }


}