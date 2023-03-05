package com.example.todayrecipe.jwt;

import com.example.todayrecipe.dto.user.LoginReqDTO;
import com.example.todayrecipe.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class JwtProvider {
    //토큰을 발급하거나, 토큰을 유저 객체로 바꾸는 클래스

    private final JwtProperties jwtProperties;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    //토큰 발급, 로그인 서비스에서 사용
    public String makeToken(User user) {
        Date date = new Date();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + Duration.ofMinutes(30).toMillis()))
                .claim("email", user.getEmail())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }
    //토큰에서 userDTO로 바꿈, JWT 필터에서 사용 유효성 검사해야함
    public LoginReqDTO tokenToUser(String token) {
        Claims claims = null;
        if (token != null) {
            try {
                if (validationAuthorizationHeader(token)) {
                    token = extractToken(token);
                    claims = parsingToken(token);
                    return new LoginReqDTO(claims);
                }
            } catch (Exception err) {
                err.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 헤더값이 유효한 지 검사해주는 메소드
     * @param header
     * @return
     */
    private boolean validationAuthorizationHeader(String header) {
        if (header == null || !header.startsWith(jwtProperties.getTokenPrefix())) {
            return false;
        }
        return true;
    }

    /**
     * 토큰 값을 클레임으로 바꿔주는 메소드
     * @param token
     * @return
     */
    private Claims parsingToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 토큰 헤더 떼고 토큰 값만 가져오는 메소드
     * @param authorizatinoHeader
     * @return
     */
    private String extractToken(String authorizatinoHeader) {
        return authorizatinoHeader.substring(jwtProperties.getTokenPrefix().length());
    }
}
