package com.sian.community_api.service;

import com.sian.community_api.config.UserValidator;
import com.sian.community_api.dto.user.TokenResponse;
import com.sian.community_api.dto.user.UserLoginRequest;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.config.JwtTokenProvider;
import com.sian.community_api.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    public TokenResponse login(UserLoginRequest request) {

        User user = userValidator.findValidUserByEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "invalid_credentials", "이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(user.getId());
        return new TokenResponse(token);
    }
}
