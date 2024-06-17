package com.project.customer.user.service;

import com.project.core.common.token.TokenProvider;
import com.project.customer.common.service.redis.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

    @Mock
    private RedisService redisService;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    private long REFRESH_TOKEN_EXPIRE_TIME ;

    private String SECRET_KEY = "dfecvdsgvrg456tt54trgfvfdsgerhntry5342wefrehfg";

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
        String token = "refresh..en";
        String tokenInfo = "email";
        given(tokenProvider.getSubject(token, "email")).willReturn(tokenInfo);

        //when
        String response = refreshTokenService.getTokenInfo(token, "email");

        //then
        Assertions.assertThat(response).isEqualTo(tokenInfo);
    }

    @Test
    void validateToken_success() {
        //given
        String email = "email";
        String token = "refreshToken";

        given(redisService.getData(REDIS_REFRESH+email)).willReturn(token);
        given(tokenProvider.validateRefreshToken(token)).willReturn(true);
        given(tokenProvider.getSubject(token, "email")).willReturn(email);
        given(refreshTokenService.getTokenInfo(token, "email")).willReturn(email);

        //when
        boolean response = refreshTokenService.validateToken(email);

        //then
        Assertions.assertThat(response).isTrue();
    }


}