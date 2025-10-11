package com.sian.community_api.controller;

import com.sian.community_api.dto.user.TokenResponse;
import com.sian.community_api.dto.user.UserLoginRequest;
import com.sian.community_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@Valid @RequestBody UserLoginRequest request) {
        return authService.login(request);
    }
}
