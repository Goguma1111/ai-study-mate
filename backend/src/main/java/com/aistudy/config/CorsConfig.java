package com.aistudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Spring Security를 쓰기 시작하면, CORS 설정은 WebMvcConfigurer 방식이 아니라
 * 이렇게 CorsConfigurationSource라는 "Bean"으로 등록해야 SecurityConfig에서 인식할 수 있습니다.
 * (SecurityConfig의 http.cors(...) 부분이 이 Bean을 찾아서 사용합니다)
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // React 개발 서버 주소
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*")); // Authorization 헤더(토큰)도 포함해서 전부 허용
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 위 설정 적용
        return source;
    }
}
