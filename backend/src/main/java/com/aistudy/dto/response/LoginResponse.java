package com.aistudy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 성공하면 프론트는 이 형태의 JSON을 받습니다.
 * 예: { "token": "eyJhbGciOi...", "email": "test@test.com", "name": "홍길동" }
 *
 * 프론트는 token을 localStorage에 저장해뒀다가, 이후 모든 API 요청 헤더에 담아 보냅니다.
 */
@Getter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String email;
    private String name;
}
