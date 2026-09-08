package com.aistudy.config;

import com.aistudy.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 이 프로젝트의 "보안 규칙"을 정의하는 곳입니다.
 * - 회원가입/로그인 API는 로그인 없이도 호출 가능해야 하고 (permitAll)
 * - 나머지 API(과목 등록, 학습 계획 등)는 로그인한 사용자만 호출 가능해야 합니다 (authenticated)
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 비밀번호를 암호화(해싱)할 때 쓸 도구. BCrypt는 같은 비밀번호를 넣어도 매번 다른 결과가 나오는
    // 단방향 암호화 방식이라 안전합니다 (원본으로 되돌릴 수 없음, 비교만 가능).
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 위 CorsConfig에서 만든 CorsConfigurationSource Bean을 사용하도록 연결
                .cors(cors -> {})

                // REST API는 세션/쿠키 기반이 아니라 토큰 기반이라 CSRF 보호가 필요 없음
                .csrf(csrf -> csrf.disable())

                // 세션을 서버에 저장하지 않음 (STATELESS) - 매 요청마다 토큰으로만 인증 확인
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // 아래 경로들은 로그인 없이도 접근 가능
                        .requestMatchers("/api/auth/**", "/api/health").permitAll()
                        // 그 외 모든 요청은 로그인(토큰)이 있어야 함
                        .anyRequest().authenticated()
                )

                // 우리가 만든 JWT 필터를 스프링 시큐리티의 기본 인증 필터보다 먼저 실행되도록 등록
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
