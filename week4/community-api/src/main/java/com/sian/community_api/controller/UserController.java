package com.sian.community_api.controller;

import com.sian.community_api.dto.user.UserSignupRequest;
import com.sian.community_api.dto.user.UserSignupResponse;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.jwt.JwtTokenProvider;
import com.sian.community_api.model.User;
import com.sian.community_api.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입 API
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserSignupResponse signup(@Valid @RequestBody UserSignupRequest request) {
        if (!request.isPasswordConfirmed()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "password_mismatch", "비밀번호가 일치하지 않습니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .profileImage(request.getProfileImage())
                .build();

        User created = userService.createUser(user);
        return UserSignupResponse.from(created);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        // 헤더에서 토큰 추출
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "missing_token", "토큰이 없습니다.");
        }
        String token = authHeader.substring(7); // "Bearer " 제거

        // 토큰 검증
        if (!jwtTokenProvider.validateToken(token)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "invalid_token", "토큰이 유효하지 않습니다.");
        }

        String email = jwtTokenProvider.getEmailFromToken(token);

        userService.deleteUser(id, email);

        return Map.of("message", "회원 탈퇴가 완료되었습니다.");
    }

}
