
## 1단계에서 확인할 것
"React 화면에서 회원가입 → 로그인 → JWT 토큰 발급 → 로그인 상태 유지"까지
전체 인증 흐름이 정상 동작하면 1단계는 성공입니다.

---

## 1. DB 준비하기
1. 로컬에 MySQL이 설치되어 있어야 합니다.
2. 터미널에서 MySQL에 접속합니다.
```bash
   mysql -u root -p --port=8080
```
3. `database/schema.sql` 내용을 그대로 붙여넣어 실행합니다. (DB와 테이블 3개가 생성됩니다)

## 2. 백엔드 실행하기
> ⚠️ 이 프로젝트는 MySQL을 **8080번 포트**로 설치한 것에 맞춰, 백엔드 서버는 **8081번 포트**를 쓰도록 설정되어 있습니다.

1. `backend/src/main/resources/application.yml`에서 `password` 값을 본인 MySQL 비밀번호로 바꿉니다.
2. 터미널에서 백엔드를 실행합니다.
```bash
   cd backend
   ./gradlew bootRun        # 윈도우는 .\gradlew.bat bootRun
```
3. 콘솔에 `Started AiStudyMateApplication`이 뜨면 성공입니다.

## 3. 프론트엔드 실행하기
```bash
cd frontend
npm install
npm run dev
```
터미널에 뜨는 주소(보통 `http://localhost:5173`)로 접속합니다.

## 4. 성공 확인
1. `/signup`에서 이메일/비밀번호/이름으로 회원가입
2. `/login`에서 로그인
3. "반가워요, ○○님!" 대시보드 화면이 뜨면 성공
4. 새로고침해도 로그인 상태가 유지되는지 확인 (JWT가 localStorage에 저장되어 있기 때문)

문제가 생기면:
- 8081 포트에서 백엔드가 켜져 있는지 확인
- `application.yml`의 MySQL 비밀번호/포트가 맞는지 확인
- CORS 에러가 뜨면 `CorsConfig.java`의 허용 주소(5173)를 확인

---

## 완료된 기능
- ✅ 0단계: 프로젝트 뼈대 (프론트 ↔ 백엔드 ↔ DB 연결)
- ✅ 1단계: JWT 기반 회원가입 / 로그인

## 다음 단계 (2단계)
과목 등록 기능(과목명, 시험일, 공부범위, 현재진도)을 구현합니다.