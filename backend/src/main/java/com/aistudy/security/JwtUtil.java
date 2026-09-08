package com.aistudy.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT(로그인 토큰)를 다루는 역할만 전담하는 클래스입니다.
 *
 * JWT가 뭔지 간단히 설명하면:
 * - 로그인에 성공하면 서버가 "이 사람은 인증된 사용자다"라는 정보를 담은 암호화된 문자열(토큰)을 만들어 줍니다.
 * - 프론트는 이 토큰을 저장해뒀다가, 이후 모든 요청에 이 토큰을 같이 보냅니다.
 * - 서버는 토큰만 보고 "아, 이 사람 로그인 했었구나"를 확인하고, 매번 DB에서 세션을 찾을 필요가 없어요.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    // application.yml에 적은 문자열(secret)을 실제 암호화 키 형태로 변환합니다.
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /** 이메일을 기반으로 새 토큰을 발급합니다. (로그인 성공 시 호출) */
    public String generateToken(String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(email)        // 토큰 안에 "이 토큰은 누구 것인지" 이메일을 담음
                .issuedAt(now)          // 발급 시각
                .expiration(expiry)     // 만료 시각
                .signWith(getSigningKey())
                .compact();
    }

    /** 토큰 안에서 이메일을 꺼냅니다. (요청이 들어올 때마다 "이 토큰 누구 거지?" 확인용) */
    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

    /** 토큰이 위조되지 않았고, 만료되지 않았는지 확인합니다. */
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false; // 서명이 이상하거나, 만료되었거나, 형식이 잘못된 경우
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
