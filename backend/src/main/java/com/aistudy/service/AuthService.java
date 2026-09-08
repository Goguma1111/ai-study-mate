package com.aistudy.service;

import com.aistudy.domain.User;
import com.aistudy.dto.request.LoginRequest;
import com.aistudy.dto.request.SignupRequest;
import com.aistudy.dto.response.LoginResponse;
import com.aistudy.repository.UserRepository;
import com.aistudy.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final 필드들을 매개변수로 받는 생성자를 자동으로 만들어줌 (의존성 주입)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /** 회원가입: 이메일 중복 체크 → 비밀번호 암호화 → 저장 */
    public void signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        // 원본 비밀번호를 그대로 저장하면 절대 안 됩니다. 반드시 암호화(해싱)해서 저장합니다.
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(request.getEmail(), encodedPassword, request.getName());
        userRepository.save(user);
    }

    /** 로그인: 이메일로 사용자 조회 → 비밀번호 일치 확인 → 토큰 발급 */
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        // matches()는 "입력한 원본 비밀번호를 암호화했을 때 저장된 값과 같은가"를 비교해줍니다.
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponse(token, user.getEmail(), user.getName());
    }
}
