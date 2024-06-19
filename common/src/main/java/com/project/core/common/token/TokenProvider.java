package com.project.core.common.token;

import com.project.core.domain.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class TokenProvider {

    @Value("${security.jwt.access-expire}")
    private long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${security.jwt.secret}")
    private String SECRET_KEY;

    public static final String TOKEN_PREFIX = "Bearer ";
    private static final String ACCESS_SUBJECT = "AccessToken";
    private static final String REFRESH_SUBJECT = "RefreshToken";

    public String generateAccessToken(final String email, final Role role){
        final Date now = new Date(System.currentTimeMillis());
        final Date expiredDate = new Date(now.getTime()+ACCESS_TOKEN_EXPIRE_TIME);
        return Jwts.builder()
                .setSubject(ACCESS_SUBJECT)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .claim("email",email)
                .claim("role",role)
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY.getBytes())
                .compact();
    }

    public String generateRefreshToken(final String email, final long time){
        final Date now = new Date();
        final Date expiredDate = new Date(now.getTime()+time);
        return Jwts.builder()
                .setSubject(REFRESH_SUBJECT)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .claim("email",email)
                .signWith(SignatureAlgorithm.HS256,createSecretKey())
                .compact();
    }

    private SecretKey createSecretKey() {
        final byte[] secretKeyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(secretKeyBytes);
    }

    public boolean validateAccessToken(final String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        final Claims claims = parseClaims(token);
        return isToken(claims, ACCESS_SUBJECT) && isNotExpired(claims);
    }

    public boolean validateRefreshToken(final String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        final Claims claims = parseClaims(token);
        return isToken(claims, REFRESH_SUBJECT) && isNotExpired(claims);
    }

    public String getSubject(final String authorizationToken, final String subject) {
        return parseClaims(authorizationToken).get(subject, String.class);
    }

    private Claims parseClaims(final String authorizationToken) {
        final String token = authorizationToken.substring(TOKEN_PREFIX.length());
        return Jwts.parserBuilder()
                .setSigningKey(createSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isToken(final Claims claims, final String subject) {
        return claims.getSubject().equals(subject);
    }

    private boolean isNotExpired(final Claims claims) {
        return claims.getExpiration().after(new Date());
    }

    public AuthUser getAuthMember(final String authorizationHeader) {
        final Claims claims = parseClaims(authorizationHeader);
        final String email = claims.get("email" , String.class);
        final Role role= Role.valueOf((String) claims.get("role"));
        return new AuthUser(email, role);
    }
}
