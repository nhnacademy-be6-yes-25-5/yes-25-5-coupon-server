package com.nhnacademy.couponapi.common;

import com.nhnacademy.couponapi.common.exception.JwtException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtProvider {

    private final SecretKey secretKey;

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();

            if (claims.getExpiration().before(new Date())) {
                throw new JwtException(
                        ErrorStatus.toErrorStatus("토큰의 유효시간이 지났습니다.", 401, LocalDateTime.now())
                );
            }

            return true;
        } catch (SignatureException e) {
            throw new JwtException(
                    ErrorStatus.toErrorStatus("시크릿키 변경이 감지되었습니다.", 401, LocalDateTime.now())
            );
        }
    }

    public Long getUserIdFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("customerId", Long.class);
    }

}
