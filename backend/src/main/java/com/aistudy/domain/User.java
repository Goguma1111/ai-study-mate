package com.aistudy.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * users 테이블과 매칭되는 클래스입니다.
 * @Entity: "이 클래스는 DB 테이블과 연결된다"는 표시
 * @Table(name = "users"): 실제 연결될 테이블 이름 지정
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor // JPA는 매개변수 없는 생성자가 필수로 있어야 함
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id는 DB가 자동으로 1, 2, 3... 증가시킴
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // 주의: 여기엔 암호화된(BCrypt) 비밀번호만 저장됩니다. 원본 비밀번호는 절대 저장하지 않아요.
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "daily_available_minutes")
    private Integer dailyAvailableMinutes = 120; // 기본값: 하루 2시간

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 이 객체가 처음 저장되기 직전에 자동으로 실행되어 생성 시각을 기록합니다.
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // 회원가입 시 필요한 값만 받아서 User 객체를 만드는 생성자
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
