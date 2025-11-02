package com.sian.community_api.controller;

import com.sian.community_api.config.AuthUtil;
import com.sian.community_api.dto.common.ApiResponse;
import com.sian.community_api.dto.user.TokenResponse;
import com.sian.community_api.dto.user.UserLoginRequest;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthUtil authUtil;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<TokenResponse> login(@Valid @RequestBody UserLoginRequest request) {

        TokenResponse response = authService.login(request);
        return ApiResponse.ok(response);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = authUtil.extractUserId(authHeader);
        authService.logout(userId);
        return ApiResponse.ok(null);
    }

    @PostMapping("/refresh")
    public ApiResponse<TokenResponse> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new CustomException(
                    HttpStatus.BAD_REQUEST,
                    "missing_refresh_token",
                    "리프레시 토큰이 필요합니다."
            );
        }

        TokenResponse newTokens = authService.reissue(refreshToken);
        return ApiResponse.ok(newTokens);
    }
}
