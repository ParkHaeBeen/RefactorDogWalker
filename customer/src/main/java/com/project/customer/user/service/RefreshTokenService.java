package com.project.customer.user.service;

import com.project.core.common.token.TokenProvider;
import com.project.customer.common.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RedisService redisService;
    private final TokenProvider tokenProvider;

    @Value("${security.jwt.refresh-expire}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    private static final String REDIS_REFRESH = "REDIS_REFRESH";

    public String generateToken(final String email) {
        final String token = tokenProvider.generateRefreshToken(email, REFRESH_TOKEN_EXPIRE_TIME);
        redisService.addData(REDIS_REFRESH+email, token, REFRESH_TOKEN_EXPIRE_TIME);
        return token;
    }

    public String getTokenInfo(final String token, final String subject) {
        return tokenProvider.getSubject(token, subject);
    }

    public boolean validateToken(final String email) {
        final String token = String.valueOf(redisService.getData(REDIS_REFRESH + email));

        if(token.isBlank() || !tokenProvider.validateRefreshToken(token)) {
            return false;
        }

        return getTokenInfo(token, "email").equals(email);
    }
}
