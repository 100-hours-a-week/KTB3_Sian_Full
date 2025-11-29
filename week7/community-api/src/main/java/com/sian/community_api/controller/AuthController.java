package com.sian.community_api.controller;

import com.sian.community_api.dto.common.ApiResponse;
import com.sian.community_api.dto.user.LoginTokens;
import com.sian.community_api.dto.user.TokenResponse;
import com.sian.community_api.dto.user.UserLoginRequest;
import com.sian.community_api.dto.user.UserLoginResponse;
import com.sian.community_api.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인
     * AccessToken → JSON
     * RefreshToken → HttpOnly Cookie
     */
    @PostMapping("/login")
    public ApiResponse<UserLoginResponse> login(
            @Valid @RequestBody UserLoginRequest request,
            HttpServletResponse response
    ) {
        LoginTokens tokens = authService.login(request);

        // === Refresh Token 쿠키 저장 ===
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", tokens.getRefreshToken())
                .httpOnly(true)
                .secure(false) // dev= false / prod= true
                .sameSite("None")
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7일
                .build();

        response.addHeader("Set-Cookie", refreshCookie.toString());

        // AccessToken은 JSON으로 반환
        return ApiResponse.ok(UserLoginResponse.of(tokens.getUser(), tokens.getAccessToken()));
    }


    /**
     * 로그아웃
     * - DB의 refreshToken 제거
     * - 쿠키에서도 refreshToken 삭제
     */
    @PostMapping("/logout")
    public ApiResponse<Void> logout(
            Authentication authentication,
            HttpServletResponse response
    ) {
        Long userId = Long.valueOf(authentication.getName());
        authService.logout(userId);

        // === RefreshToken 삭제 ===
        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader("Set-Cookie", deleteCookie.toString());

        return ApiResponse.ok(null);
    }


    /**
     * AccessToken 재발급
     * RefreshToken은 쿠키에서 자동으로 가져옴
     */
    @PostMapping("/refresh")
    public ApiResponse<TokenResponse> refresh(
            @CookieValue(value = "refreshToken", required = false) String oldRefreshToken,
            HttpServletResponse response
    ) {
        // === RefreshToken 쿠키 없음 ===
        if (oldRefreshToken == null) {
            return ApiResponse.error(401, "리프레시 토큰이 없습니다.");
        }

        LoginTokens newTokens = authService.reissue(oldRefreshToken);

        // === RefreshToken 새로 갱신 ===
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", newTokens.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("None")
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .build();

        response.addHeader("Set-Cookie", refreshCookie.toString());

        // AccessToken은 JSON으로 내려줌
        return ApiResponse.ok(new TokenResponse(newTokens.getAccessToken()));
    }

}
