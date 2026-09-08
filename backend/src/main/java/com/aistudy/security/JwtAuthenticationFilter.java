package com.aistudy.security;

import com.aistudy.domain.User;
import com.aistudy.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

/**
 * 이 필터는 "문지기" 역할을 합니다. /api/로 들어오는 모든 요청이 실제 Controller에 도달하기 전에
 * 이 필터를 먼저 거칩니다.
 *
 * 동작 순서:
 * 1. 요청 헤더에서 "Authorization: Bearer {토큰}" 을 찾는다.
 * 2. 토큰이 있고 유효하면, 그 토큰의 주인(이메일)을 DB에서 조회한다.
 * 3. 찾았으면 "이 요청은 인증된 사용자의 요청이다"라고 Spring Security에게 등록한다.
 * 4. 다음 필터(혹은 실제 Controller)로 요청을 넘긴다.
 *
 * OncePerRequestFilter를 상속하면 요청 1건당 이 필터가 딱 한 번만 실행되는 것이 보장됩니다.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // "Bearer " 이후 실제 토큰 부분만 추출

            if (jwtUtil.isTokenValid(token)) {
                String email = jwtUtil.extractEmail(token);
                Optional<User> userOptional = userRepository.findByEmail(email);

                userOptional.ifPresent(user -> {
                    // Spring Security에게 "이 사용자는 인증되었다"고 알려주는 부분
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                });
            }
        }

        filterChain.doFilter(request, response); // 다음 단계로 요청을 넘김 (여기서 안 막으면 통과)
    }
}
