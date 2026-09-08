package com.aistudy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * 이 클래스가 없으면, 예외가 발생했을 때 Spring이 기본으로 만드는 복잡하고 못생긴 에러 페이지가
 * 그대로 프론트에 전달됩니다. 여기서 미리 잡아서 { "message": "..." } 형태로 깔끔하게 바꿔줍니다.
 *
 * @RestControllerAdvice: 모든 @RestController에서 발생하는 예외를 여기서 한 번에 처리하겠다는 표시
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // AuthService에서 throw한 "이미 가입된 이메일입니다" 같은 에러를 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400
                .body(Map.of("message", e.getMessage()));
    }

    // @Valid 검증에 실패했을 때 (예: 이메일 형식이 아님, 비밀번호가 너무 짧음)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException e) {
        // 여러 필드 중 첫 번째 에러 메시지만 꺼내서 보여줌 (간단하게 처리)
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("입력값을 확인해주세요.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", message));
    }
}
