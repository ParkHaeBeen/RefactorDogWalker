package com.project.core.common.token;

import com.project.core.domain.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
public class TokenProvider {

    @Value("${security.jwt.access-expire}")
    private long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${security.jwt.secret}")
    private String SECRET_KEY;

    public static final String TOKEN_PREFIX="Bearer ";

    private static final String ACCESS_SUBJECT="AccessToken";
    private static final String REFRESH_SUBJECT="RefreshToken";

    public String generateAccessToken(final String email, final Role role){
        final Date now = new Date();
        final Date expiredDate = new Date(now.getTime()+ACCESS_TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setSubject("AccessToken")
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .claim("email",email)
                .claim("role",role)
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
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
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    public boolean validateAccessToken(final String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        final Jws<Claims> claims = parseClaims(token);
        return isToken(claims, ACCESS_SUBJECT) && isNotExpired(claims);
    }

    public boolean validateRefreshToken(final String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        final Jws<Claims> claims = parseClaims(token);
        return isToken(claims, REFRESH_SUBJECT) && isNotExpired(claims);
    }

    public Jws<Claims> parseClaims(final String authorizationToken) {
        final String token = authorizationToken.substring(TOKEN_PREFIX.length());
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
    }

    private boolean isToken(final Jws<Claims> claims, final String subject) {
        return claims.getBody().getSubject().equals(subject);
    }

    private boolean isNotExpired(final Jws<Claims> claims) {
        return claims.getBody().getExpiration().after(new Date());
    }

    public AuthMember getAuthMember(final String authorizationHeader) {
        final Jws<Claims> claims = parseClaims(authorizationHeader);
        final Claims body = claims.getBody();
        final String email = body.get("email" , String.class);
        final Role role= Role.valueOf((String) body.get("role"));
        return new AuthMember(email, role);
    }
}
