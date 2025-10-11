package com.sian.community_api.controller;

import com.sian.community_api.auth.AuthUtil;
import com.sian.community_api.dto.user.UserSignupRequest;
import com.sian.community_api.dto.user.UserSignupResponse;
import com.sian.community_api.dto.user.UserUpdateRequest;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.model.User;
import com.sian.community_api.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.sian.community_api.dto.common.ApiResponse;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthUtil authUtil;

    // 회원가입 API
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserSignupResponse> signup(@Valid @RequestBody UserSignupRequest request) {

        if (!request.isPasswordConfirmed()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "password_mismatch", "비밀번호가 일치하지 않습니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .profileImage(request.getProfileImage())
                .build();

        User created = userService.createUser(user);
        UserSignupResponse response = UserSignupResponse.from(created);

        return ApiResponse.created(response);
    }

    // 내 정보 조회
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UserSignupResponse> getMyInfo(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String email = authUtil.extractEmail(authHeader);

        User user = userService.findByEmail(email);

        return ApiResponse.ok(UserSignupResponse.from(user));
    }

    // 내 정보 수정
    @PatchMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UserSignupResponse> updateUserInfo(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody UserUpdateRequest request) {

        String email = authUtil.extractEmail(authHeader);

        User updatedUser = userService.updateUser(
                email,
                request.getNickname(),
                request.getProfileImage()
        );

        return ApiResponse.ok(UserSignupResponse.from(updatedUser));
    }


    // 회원 탈퇴
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {

        String email = authUtil.extractEmail(authHeader);

        userService.deleteUser(id, email);

        return ApiResponse.success(200, "회원탈퇴가 완료되었습니다.", null);
    }
}
