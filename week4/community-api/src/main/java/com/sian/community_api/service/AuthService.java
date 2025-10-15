package com.sian.community_api.service;

import com.sian.community_api.dto.user.TokenResponse;
import com.sian.community_api.dto.user.UserLoginRequest;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.config.JwtTokenProvider;
import com.sian.community_api.domain.User;
import com.sian.community_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    // 로그인
    public TokenResponse login(UserLoginRequest request) {

        // 이메일 틀린 경우
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(HttpStatus.UNAUTHORIZED, "invalid_credentials", "이메일 또는 비밀번호가 올바르지 않습니다."));

        // 탈퇴한 사용자인지 확인
        if (user.isDeleted()) {
            throw new CustomException(HttpStatus.FORBIDDEN, "DELETED_USER", "탈퇴한 회원의 접근입니다.");
        }

        // 비밀번호 틀린 경우
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "invalid_credentials", "이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());
        return new TokenResponse(token);
    }
}
