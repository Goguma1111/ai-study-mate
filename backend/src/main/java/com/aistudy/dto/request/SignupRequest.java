package com.aistudy.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프론트에서 회원가입 폼을 제출할 때 이 형태의 JSON을 보냅니다.
 * 예: { "email": "test@test.com", "password": "1234abcd", "name": "홍길동" }
 *
 * @NotBlank, @Email, @Size 어노테이션은 "입력값 검증" 규칙입니다.
 * 조건에 안 맞으면 Controller에 도달하기도 전에 자동으로 에러를 응답합니다.
 */
@Getter
@NoArgsConstructor
public class SignupRequest {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 4, message = "비밀번호는 4자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;
}
