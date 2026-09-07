-- 1. 먼저 DB를 만듭니다 (한 번만 실행)
CREATE DATABASE IF NOT EXISTS ai_study_mate
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE ai_study_mate;

-- 2. 회원 테이블
CREATE TABLE users (
    id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email                    VARCHAR(100) NOT NULL UNIQUE,
    password                 VARCHAR(255) NOT NULL,
    name                     VARCHAR(50)  NOT NULL,
    daily_available_minutes  INT          DEFAULT 120,
    created_at               DATETIME     DEFAULT CURRENT_TIMESTAMP
);

-- 3. 과목 테이블
CREATE TABLE subjects (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id           BIGINT       NOT NULL,
    subject_name      VARCHAR(100) NOT NULL,
    exam_date         DATE         NOT NULL,
    study_range       VARCHAR(255),
    total_amount      INT          NOT NULL,
    current_progress  INT          DEFAULT 0,
    importance        TINYINT      DEFAULT 3,
    created_at        DATETIME     DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 4. AI가 생성한 날짜별 학습 계획 테이블
CREATE TABLE study_plans (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id            BIGINT       NOT NULL,
    subject_id         BIGINT       NOT NULL,
    plan_date          DATE         NOT NULL,
    study_content      VARCHAR(255) NOT NULL,
    estimated_minutes  INT          NOT NULL,
    is_completed       BOOLEAN      DEFAULT FALSE,
    completed_at       DATETIME     NULL,
    created_at         DATETIME     DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
);
