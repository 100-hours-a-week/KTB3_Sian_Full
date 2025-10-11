package com.sian.community_api.service;

import com.sian.community_api.dto.user.TokenResponse;
import com.sian.community_api.dto.user.UserLoginRequest;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.jwt.JwtTokenProvider;
import com.sian.community_api.model.User;
import com.sian.community_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse login(UserLoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        // 이메일과 비밀번호 중 하나라도 틀린 경우
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "invalid_credentials", "이메일 또는 비밀번호를 확인해주세요.");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());
        return new TokenResponse(token);
    }
}
