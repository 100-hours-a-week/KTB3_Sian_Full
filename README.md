# 🏡 Community API

> Spring Boot 기반 커뮤니티 백엔드 REST API 서비스  
> 게시글, 댓글, 좋아요, JWT 인증 기능을 포함한 개인 프로젝트

---

## 📘 Overview

**Community API**는 Spring Boot 기반의 RESTful 백엔드 애플리케이션입니다.  
사용자 인증을 기반으로 게시글·댓글·좋아요 기능을 제공하며,  
**JWT Access/Refresh Token 구조**, **전역 예외 처리**, **JPA 연관관계**,  
**CORS/CSRF/Security 설정** 등을 직접 구축한 개인 프로젝트입니다.

---

## 영상
### 회원가입 및 로그인
https://github.com/user-attachments/assets/972caae0-ac55-48fc-90aa-5ef90d94fdbe

### 게시글 작성 및 수정
https://github.com/user-attachments/assets/b85aa95a-eea2-4257-89f7-15a051a07170

### 댓글 작성,수정,삭제 및 게시글 삭제
https://github.com/user-attachments/assets/8342e31f-1b0b-4af9-b2f1-152dbe32e47c

### 사용자 정보 수정 및 로그아웃
https://github.com/user-attachments/assets/ed7cf05c-234b-4515-bb2d-cc26f17d8144

---

## ⚙️ Tech Stack

| Category | Stack |
|-----------|--------|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.5.6 |
| **ORM & DB** | Spring Data JPA, H2 Database |
| **Build Tool** | Gradle |
| **Security** | Spring Security, JWT (Access + Refresh Token) |

---

## 🧩 Features

### 👤 User
- 회원가입 / 로그인 (JWT 발급)
- 사용자 정보 수정 (닉네임, 프로필 이미지)
- 프로필 이미지 업로드/삭제
- 닉네임 중복 검증

---

### 📝 Post
- 게시글 CRUD (작성 / 조회 / 수정 / 삭제)
- 조회수 자동 증가
- 본인 작성 글만 수정/삭제 가능
- 제목/본문 유효성 검증

---

### 💬 Comment
- 댓글 작성 / 조회 / 수정 / 삭제
- 게시글 기준 정렬
- 댓글 수 자동 증가
- 본인 작성 댓글만 수정 및 삭제 가능

---

### ❤️ Like
- 좋아요 추가/취소
- 중복 좋아요 방지
- 좋아요 수 자동 증가

---

## 🔐 JWT Authentication Flow

| 단계 | 설명 |
|------|------|
| 1️⃣ | 로그인 시 Access Token + Refresh Token 발급 |
| 2️⃣ | Refresh Token은 HttpOnly Cookie로 저장 |
| 3️⃣ | 모든 API 요청 시 Access Token 헤더 포함 |
| 4️⃣ | Access Token 만료 → Refresh Token으로 재발급 |
| 5️⃣ | Refresh Token 위조/만료 시 강제 로그아웃 처리 |

---

## 📁 Folder Structure

```
com/sian/community_api/
├── config/
│   ├── CustomAccessDeniedHandler.java
│   ├── CustomAuthenticationEntryPoint.java
│   ├── PostValidator.java
│   ├── SecurityConfig.java
│   ├── UserValidator.java
│   └── WebConfig.java
│
├── controller/
│   ├── AuthController.java
│   ├── CommentController.java
│   ├── LikeController.java
│   ├── PostController.java
│   └── UserController.java
│
├── dto/
│   ├── Comment/
│   │   ├── CommentPageResponse.java
│   │   ├── CommentRequest.java
│   │   └── CommentResponse.java
│   │
│   ├── common/
│   │   └── ApiResponse.java
│   │
│   ├── post/
│   │   ├── PostCreateRequest.java
│   │   ├── PostDetailResponse.java
│   │   ├── PostPageResponse.java
│   │   ├── PostSummaryResponse.java
│   │   └── PostUpdateRequest.java
│   │
│   └── user/
│       ├── LoginTokens.java
│       ├── TokenResponse.java
│       ├── UserLoginRequest.java
│       ├── UserLoginResponse.java
│       ├── UserPasswordUpdateRequest.java
│       ├── UserSignupRequest.java
│       ├── UserSignupResponse.java
│       └── UserUpdateRequest.java
│
├── entity/
│   ├── Comment.java
│   ├── Like.java
│   ├── Post.java
│   ├── RefreshToken.java
│   └── User.java
│
├── exception/
│   ├── CustomException.java
│   ├── ErrorResponse.java
│   └── GlobalExceptionHandler.java
│
├── init/
│   └── InitData.java
│
├── jwt/
│   ├── JwtFilter.java
│   └── TokenProvider.java
│
├── repository/
│   ├── CommentRepository.java
│   ├── LikeRepository.java
│   ├── PostRepository.java
│   ├── RefreshTokenRepository.java
│   └── UserRepository.java
│
├── service/
│   ├── post/
│   │   ├── sort/
│   │   └── PostService.java
│   │
│   ├── AuthService.java
│   ├── CommentService.java
│   ├── FileStorageService.java
│   ├── LikeService.java
│   ├── TokenService.java
│   └── UserService.java
│
└── CommunityApiApplication.java

resources/
├── application.yml
└── (정적 리소스, schema.sql, data.sql 등이 있을 경우 여기에 포함)
```

