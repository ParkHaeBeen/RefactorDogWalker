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

    @Value("${security.jwt.expire-length}")
    private long TOKEN_EXPIRE_TIME;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public static final String TOKEN_PREFIX="Bearer ";

    private static final String ACCESS_SUBJECT="AccessToken";

    public String generateToken(final String email, final Role role){
        final Date now=new Date();
        final Date validateDate=new Date(now.getTime()+TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setSubject(ACCESS_SUBJECT)
                .setIssuedAt(now)
                .setExpiration(validateDate)
                .claim("email",email)
                .claim("role",role)
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    public boolean validateToken(final String authorizationToken){
        if (!StringUtils.hasText(authorizationToken)) {
            return false;
        }
        final Jws<Claims> claims=parseClaims(authorizationToken);
        return isAccessToken(claims) && isNotExpired(claims);
    }

    private Jws<Claims> parseClaims(final String authorizationToken) {
        final String authToken = authorizationToken.substring(TOKEN_PREFIX.length());
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(authToken);
    }

    private boolean isAccessToken(final Jws<Claims> claims) {
        return claims.getBody().getSubject().equals(ACCESS_SUBJECT);
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
