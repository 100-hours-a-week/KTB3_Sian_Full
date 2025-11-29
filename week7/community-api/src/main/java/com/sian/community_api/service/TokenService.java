package com.sian.community_api.service;
import com.sian.community_api.jwt.TokenProvider;
import com.sian.community_api.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {
    private final TokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
}
