package com.aistudy.controller;

import com.aistudy.dto.request.LoginRequest;
import com.aistudy.dto.request.SignupRequest;
import com.aistudy.dto.response.LoginResponse;
import com.aistudy.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 프론트가 실제로 호출하게 될 주소:
 * POST http://localhost:8081/api/auth/signup
 * POST http://localhost:8081/api/auth/login
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok(Map.of("message", "회원가입이 완료되었습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
