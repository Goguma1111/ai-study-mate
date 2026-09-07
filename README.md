# AI Study Mate — 0단계 (프로젝트 뼈대)

## 폴더 구성
```
ai-study-mate/
 ├─ backend/    # Spring Boot 서버 (포트 8081)
 ├─ frontend/   # React 앱 (포트 5173)
 └─ database/   # MySQL 테이블 생성 스크립트
```

## 0단계에서 확인할 것
"React 화면에서 Spring Boot API를 호출해서 응답을 화면에 띄운다."
이것만 되면 0단계는 성공입니다.

---

## 1. DB 준비하기
1. 로컬에 MySQL이 설치되어 있어야 합니다 (없다면 MySQL Community Server 설치).
2. 터미널에서 MySQL에 접속합니다.
   ```bash
   mysql -u root -p
   ```
3. `database/schema.sql` 내용을 그대로 붙여넣어 실행합니다. (DB와 테이블 3개가 생성됩니다)

## 2. 백엔드 실행하기
1. `backend` 폴더를 IntelliJ IDEA(권장)로 엽니다. IntelliJ가 build.gradle을 인식해서 자동으로 필요한 라이브러리를 내려받습니다.
2. `backend/src/main/resources/application.yml`에서 `password: 여기에_본인_MySQL_비밀번호` 부분을 본인 MySQL 비밀번호로 바꿉니다.
3. `AiStudyMateApplication.java` 파일을 열고 실행(▶) 버튼을 누릅니다.
4. 콘솔에 `Tomcat started on port 8081`이 뜨면 성공입니다.
5. 브라우저에서 `http://localhost:8081/api/health` 로 들어가서 JSON 응답이 뜨는지 확인합니다.

> IntelliJ가 없다면 터미널에서 `gradle wrapper` 실행 후 `./gradlew bootRun`으로도 실행할 수 있습니다 (Gradle이 로컬에 설치되어 있어야 합니다).

## 3. 프론트엔드 실행하기
```bash
cd frontend
npm install
npm run dev
```
터미널에 뜨는 주소(보통 `http://localhost:5173`)로 접속합니다.

## 4. 성공 확인
브라우저 화면에 아래처럼 뜨면 0단계 완료입니다.
```
🐣 AI Study Mate
AI Study Mate 백엔드가 정상적으로 응답하고 있습니다!
```
글자가 빨간색으로 "백엔드 연결에 실패했어요"라고 뜬다면:
- 8080 포트에서 백엔드가 켜져 있는지 확인
- application.yml의 MySQL 비밀번호가 맞는지 확인
- CorsConfig.java의 allowedOrigins 주소가 프론트 주소(5173)와 일치하는지 확인

---

## 다음 단계 (1단계)
0단계가 성공하면, `User` 엔티티 + 회원가입/로그인(JWT) 기능을 여기에 이어서 구현합니다.
