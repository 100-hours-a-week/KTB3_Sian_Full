# 카카오테크 부트캠프 3기 Sian 과제 레포지토리
# 🏡 Community API

> Spring Boot 기반 커뮤니티 백엔드 REST API 서비스  
> 게시글, 댓글, 좋아요, JWT 인증 기능을 포함한 개인 프로젝트

---

## 📘 Overview

**Community API**는 Spring Boot로 구현한 RESTful 백엔드 애플리케이션으로,  
회원 인증을 기반으로 한 게시글 및 댓글 관리 기능을 제공합니다.  
엔티티 간 연관관계를 명확히 구분하고, 전역 예외 처리와 유효성 검증을 통해 안정적인 서버 구조를 설계했습니다.

---

## ⚙️ Tech Stack

| Category | Stack |
|-----------|--------|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.5.6 |
| **ORM / DB** | Spring Data JPA, H2 (In-Memory) |
| **Build Tool** | Gradle |
| **Auth** | JWT (Access / Refresh Token) |
| **Validation** | Jakarta Bean Validation |
| **Docs** | SpringDoc OpenAPI / Swagger UI |
| **Logging** | P6Spy, SLF4J |
| **Exception Handling** | CustomException + @ControllerAdvice |

---

## 🧩 Features

### 👤 User
- 회원가입 / 로그인 (JWT 토큰 발급)
- 사용자 정보 수정 (닉네임, 프로필 이미지)
- 닉네임 중복 검증 및 예외 처리

### 📝 Post
- 게시글 작성 / 조회 / 수정 / 삭제
- 조회수 자동 증가 (`incrementViewCount()`)
- 제목 및 내용 유효성 검증
- 예외 처리 (존재하지 않는 게시글, 잘못된 입력값 등)

### 💬 Comment
- 게시글별 댓글 작성 / 조회 / 삭제
- 작성자 권한 검증
- 댓글 수 자동 카운트 유지

### ❤️ Like
- 좋아요 추가 / 취소
- 중복 좋아요 방지 (`existsByPostAndUser`)
- 게시글의 좋아요 수 자동 증가
- DB 제약 조건: `unique (user_id, post_id)`

---

## 🔐 Auth Flow (JWT)

| 단계 | 설명 |
|------|------|
| 1️⃣ | 로그인 시 Access Token + Refresh Token 발급 |
| 2️⃣ | 모든 요청은 `Authorization: Bearer <AccessToken>` 헤더 포함 |
| 3️⃣ | Access Token 만료 시 Refresh Token으로 재발급 |
| 4️⃣ | CustomException + @ControllerAdvice 로 일관된 에러 응답 처리 |

---

## 🧪 API Endpoints

| Method | Endpoint | Description |
|--------|-----------|-------------|
| `POST` | `/auth/signup` | 회원가입 |
| `POST` | `/auth/login` | 로그인 (JWT 발급) |
| `GET` | `/posts` | 게시글 목록 조회 |
| `GET` | `/posts/{id}` | 게시글 상세 조회 |
| `POST` | `/posts` | 게시글 작성 |
| `PATCH` | `/posts/{id}` | 게시글 수정 |
| `DELETE` | `/posts/{id}` | 게시글 삭제 |
| `POST` | `/posts/{id}/likes` | 좋아요 추가 |
| `DELETE` | `/posts/{id}/likes` | 좋아요 취소 |
| `POST` | `/posts/{id}/comments` | 댓글 작성 |
| `GET` | `/posts/{id}/comments` | 댓글 목록 조회 |

---
