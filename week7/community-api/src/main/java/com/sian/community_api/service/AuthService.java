package com.sian.community_api.service;

import com.sian.community_api.config.UserValidator;
import com.sian.community_api.dto.user.LoginTokens;
import com.sian.community_api.dto.user.UserLoginRequest;
import com.sian.community_api.entity.RefreshToken;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.entity.User;
import com.sian.community_api.jwt.TokenProvider;
import com.sian.community_api.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;
    private final RefreshTokenRepository refreshTokenRepository;

    public LoginTokens login(UserLoginRequest request) {

        User user = userValidator.findValidUserByEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "invalid_credentials", "이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        // 토큰 생성
        String accessToken = tokenProvider.generateAccessToken(user.getId());
        String refreshToken = tokenProvider.generateRefreshToken(user.getId());

        // refresh token DB 저장 or 업데이트
        refreshTokenRepository.findByUserId(user.getId())
                .ifPresentOrElse(
                        savedToken -> {
                            savedToken.updateToken(refreshToken);
                            savedToken.updateExpireAt();
                        },
                        () -> refreshTokenRepository.save(
                                RefreshToken.builder()
                                        .user(user)
                                        .token(refreshToken)
                                        .build()
                        )
                );

        return new LoginTokens(
                user,
                accessToken,
                refreshToken
        );
    }

    public void logout(Long userId) {
        RefreshToken token = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new CustomException(HttpStatus.BAD_REQUEST, "already_logged_out", "이미 로그아웃된 사용자입니다.")
                );

        refreshTokenRepository.delete(token);
    }

    public LoginTokens reissue(String oldRefreshToken) {

        RefreshToken savedToken = refreshTokenRepository.findByToken(oldRefreshToken)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.UNAUTHORIZED,
                        "invalid_refresh_token",
                        "유효하지 않은 리프레시 토큰입니다."
                ));

        if (savedToken.isExpired()) {
            refreshTokenRepository.delete(savedToken);
            throw new CustomException(
                    HttpStatus.UNAUTHORIZED,
                    "expired_refresh_token",
                    "리프레시 토큰이 만료되었습니다. 다시 로그인해주세요."
            );
        }

        User user = savedToken.getUser();

        String newAccessToken = tokenProvider.generateAccessToken(user.getId());
        String newRefreshToken = tokenProvider.generateRefreshToken(user.getId());

        savedToken.updateToken(newRefreshToken);
        savedToken.updateExpireAt();

        return new LoginTokens(
                user,
                newAccessToken,
                newRefreshToken
        );
    }

}
