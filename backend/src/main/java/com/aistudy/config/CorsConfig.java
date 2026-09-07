package com.aistudy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 브라우저는 기본적으로 "다른 출처(origin)"로의 요청을 막습니다.
 * React는 localhost:5173, 백엔드는 localhost:8080 으로 포트가 다르기 때문에
 * 이 설정이 없으면 프론트에서 API 호출 시 CORS 에러가 발생합니다.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")                 // /api로 시작하는 모든 요청에 대해
                .allowedOrigins("http://localhost:5173") // 이 주소에서 오는 요청만 허용
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
