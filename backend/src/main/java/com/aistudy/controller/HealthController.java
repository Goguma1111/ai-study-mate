package com.aistudy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 0단계 목표 확인용 컨트롤러입니다.
 * 브라우저에서 http://localhost:8080/api/health 로 접속하거나
 * React에서 이 주소로 요청을 보내서 정상 응답이 오는지 확인합니다.
 *
 * @RestController: 이 클래스가 REST API 요청을 처리한다는 표시
 * @GetMapping("/api/health"): GET /api/health 요청이 오면 이 메서드를 실행
 */
@RestController
public class HealthController {

    @GetMapping("/api/health")
    public Map<String, String> health() {
        return Map.of(
                "status", "OK",
                "message", "AI Study Mate 백엔드가 정상적으로 응답하고 있습니다!"
        );
    }
}
