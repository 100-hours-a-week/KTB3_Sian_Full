package com.sian.community_api.service;

import com.sian.community_api.config.JwtTokenProvider;
import com.sian.community_api.dto.user.TokenResponse;
import com.sian.community_api.entity.RefreshToken;
import com.sian.community_api.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
}
