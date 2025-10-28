package com.sian.community_api.config;

import com.sian.community_api.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final JwtTokenProvider jwtTokenProvider;

    // 토큰 유효성 검증 및 이메일 추출
    public Long extractUserId(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "missing_token", "토큰이 필요합니다.");
        }

        String token = authHeader.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "invalid_token", "토큰이 유효하지 않습니다.");
        }

        return jwtTokenProvider.getUserIdFromToken(token);
    }
}
