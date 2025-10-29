# 카카오테크 부트캠프 3기 Sian 과제 레포지토리
🏡 Community API

사용자 인증, 게시글 CRUD, 댓글, 좋아요 기능을 포함한 커뮤니티 백엔드 REST API 서비스

📘 Overview

Community API는 Spring Boot 기반의 RESTful 백엔드 애플리케이션으로,
게시글과 댓글, 좋아요 기능을 중심으로 한 간단한 커뮤니티 서비스를 제공합니다.
JWT 인증 방식을 통해 사용자 인증을 처리하고, 엔티티 간 연관관계를 명확하게 분리하여 유지보수성을 높였습니다.

⚙️ Tech Stack
Category	Stack
Language	Java 21
Framework	Spring Boot 3.5.6
ORM / DB	Spring Data JPA, H2 (In-Memory)
Build Tool	Gradle
Auth	JWT (Access / Refresh Token)
Validation	Bean Validation (Jakarta Validation)
Docs	SpringDoc / Swagger UI
Logging	P6Spy, SLF4J
Exception Handling	CustomException + @ControllerAdvice

📁 Project Structure
community-api
 ┣ 📂 controller        # API entrypoint (Controller layer)
 ┣ 📂 service           # Business logic
 ┣ 📂 repository        # JPA repositories
 ┣ 📂 entity            # JPA Entities (User, Post, Comment, Like)
 ┣ 📂 validator         # Entity validation helpers
 ┣ 📂 auth              # JWT Provider, Auth utilities
 ┣ 📂 common            # ApiResponse, exception handling
 ┣ 📜 CommunityApiApplication.java
 ┗ 📜 build.gradle

🧩 Features
👤 User

회원가입 / 로그인 (JWT 발급)

사용자 정보 조회 및 수정 (프로필 이미지, 닉네임 등)

닉네임 중복 검증 및 예외 처리

📝 Post

게시글 CRUD

조회수 자동 증가 (incrementViewCount())

제목/내용 유효성 검증

예외 처리 (존재하지 않는 게시글, 입력값 누락 등)

💬 Comment

게시글별 댓글 작성 / 조회 / 삭제

댓글 작성자 권한 검증

댓글 수 자동 카운트 유지

❤️ Like

좋아요 추가 / 취소

중복 좋아요 방지 (existsByPostAndUser)

게시글의 좋아요 수 자동 증가

DB 유니크 제약 조건 (unique (user_id, post_id))

🔐 Auth Flow (JWT)
Step	Description
1️⃣	로그인 시 Access Token + Refresh Token 발급
2️⃣	모든 인증 필요한 요청은 Authorization: Bearer <token> 헤더 포함
3️⃣	토큰 만료 시 Refresh Token으로 재발급 요청
4️⃣	CustomException + @ControllerAdvice 로 예외 일원화 처리

🧪 Example API Endpoints
Method	Endpoint	Description
POST	/auth/signup	회원가입
POST	/auth/login	로그인 (JWT 발급)
GET	/posts	게시글 목록 조회
GET	/posts/{id}	게시글 상세 조회
POST	/posts	게시글 작성
PATCH	/posts/{id}	게시글 수정
DELETE	/posts/{id}	게시글 삭제
POST	/posts/{id}/likes	게시글 좋아요
DELETE	/posts/{id}/likes	좋아요 취소
POST	/posts/{id}/comments	댓글 작성
GET	/posts/{id}/comments	댓글 목록 조회
