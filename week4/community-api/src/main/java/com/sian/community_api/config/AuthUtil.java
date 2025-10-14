package com.sian.community_api.config;

import com.sian.community_api.exception.CustomException;
import com.sian.community_api.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final JwtTokenProvider jwtTokenProvider;

    public String extractEmail(String authHeader) {
        if (authHeader == null) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "missing_token", "토큰이 없습니다.");
        }

        if (!authHeader.startsWith("Bearer ")) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "invalid_token_format", "올바르지 않은 토큰 형식입니다.");
        }

        String token = authHeader.substring(7); // "Bearer " 제거

        if (!jwtTokenProvider.validateToken(token)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "invalid_token", "토큰이 유효하지 않습니다.");
        }

        return jwtTokenProvider.getEmailFromToken(token);
    }
}
