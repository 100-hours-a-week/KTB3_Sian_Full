package com.sian.community_api.controller;

import com.sian.community_api.dto.user.UserSignupRequest;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.model.User;
import com.sian.community_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 API
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User signup(@Valid @RequestBody UserSignupRequest request) {
        if (!request.isPasswordConfirmed()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "password_mismatch", "비밀번호가 다릅니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .profileImage(request.getProfileImage())
                .build();

        return userService.createUser(user);
    }

}
