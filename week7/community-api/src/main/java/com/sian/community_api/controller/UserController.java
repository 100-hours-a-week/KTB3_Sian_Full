package com.sian.community_api.controller;

import com.sian.community_api.config.AuthUtil;
import com.sian.community_api.config.UserValidator;
import com.sian.community_api.dto.user.UserPasswordUpdateRequest;
import com.sian.community_api.dto.user.UserSignupRequest;
import com.sian.community_api.dto.user.UserSignupResponse;
import com.sian.community_api.dto.user.UserUpdateRequest;
import com.sian.community_api.entity.User;
import com.sian.community_api.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.sian.community_api.dto.common.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthUtil authUtil;
    private final UserValidator userValidator;

    @PostMapping
    public ApiResponse<UserSignupResponse> signup(@Valid @RequestBody UserSignupRequest request) {

        User created = userService.createUser(request);
        UserSignupResponse response = UserSignupResponse.from(created);

        return ApiResponse.created(response);
    }

    @GetMapping("/me")
    public ApiResponse<UserSignupResponse> getMyInfo(
            @RequestHeader(value = "Authorization") String authHeader) {

        Long userId = authUtil.extractUserId(authHeader);
        User user = userValidator.findValidUserById(userId);
        return ApiResponse.ok(UserSignupResponse.from(user));
    }

    @PatchMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<UserSignupResponse> updateUserInfo(
            @RequestHeader("Authorization") String authHeader,
            @RequestPart("nickname") String nickname,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        Long userId = authUtil.extractUserId(authHeader);

        User updatedUser = userService.updateUser(userId, nickname, image);

        return ApiResponse.ok(UserSignupResponse.from(updatedUser));
    }

    @DeleteMapping("/me")
    public ApiResponse<Void> deleteUser(@RequestHeader("Authorization") String authHeader) {

        Long userId = authUtil.extractUserId(authHeader);
        userService.deleteUser(userId);
        return ApiResponse.success(200, "회원탈퇴가 완료되었습니다.", null);
    }

    @PatchMapping("/me/password")
    public ApiResponse<Void> updatePassword(@RequestHeader("Authorization") String authHeader,@Valid @RequestBody UserPasswordUpdateRequest request ) {
        Long userId = authUtil.extractUserId(authHeader);
        userService.updatePassword(userId, request.getNewPassword(), request.getNewPasswordConfirm());
        return ApiResponse.success(200, "비밀번호가 성공적으로 변경되었습니다.", null);
    }
}
