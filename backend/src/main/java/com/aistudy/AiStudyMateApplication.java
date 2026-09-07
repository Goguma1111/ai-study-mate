package com.aistudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 이 클래스가 프로젝트의 "시작 버튼"입니다.
 * main() 메서드를 실행하면 내장 웹서버(Tomcat)가 켜지고,
 * 8080번 포트에서 요청을 기다리기 시작합니다.
 */
@SpringBootApplication
public class AiStudyMateApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiStudyMateApplication.class, args);
    }
}
