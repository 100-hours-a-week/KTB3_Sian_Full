package com.sian.community_api.controller;

import com.sian.community_api.config.UserValidator;
import com.sian.community_api.dto.user.UserPasswordUpdateRequest;
import com.sian.community_api.dto.user.UserSignupResponse;
import com.sian.community_api.entity.User;
import com.sian.community_api.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.sian.community_api.dto.common.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    @PostMapping (consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<UserSignupResponse> signup(
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("nickname") String nickname,
            @RequestPart(value = "image", required = false) MultipartFile image
            ) {

        User created = userService.createUser(email, password, nickname, image);
        UserSignupResponse response = UserSignupResponse.from(created);

        return ApiResponse.created(response);
    }

    @GetMapping("/me")
    public ApiResponse<UserSignupResponse> getMyInfo(
            Authentication authentication) {

        Long userId = Long.valueOf(authentication.getName());
        User user = userValidator.findValidUserById(userId);
        return ApiResponse.ok(UserSignupResponse.from(user));
    }

    @PatchMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<UserSignupResponse> updateUserInfo(
            Authentication authentication,
            @RequestPart("nickname") String nickname,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "profileDeleted", required = false) String profileDeleted
    ) {
        Long userId = Long.valueOf(authentication.getName());

        User updatedUser = userService.updateUser(userId, nickname, image, profileDeleted);

        return ApiResponse.ok(UserSignupResponse.from(updatedUser));
    }

    @DeleteMapping("/me")
    public ApiResponse<Void> deleteUser(Authentication authentication) {

        Long userId = Long.valueOf(authentication.getName());
        userService.deleteUser(userId);
        return ApiResponse.success(200, "회원탈퇴가 완료되었습니다.", null);
    }

    @PatchMapping("/me/password")
    public ApiResponse<Void> updatePassword(Authentication authentication,@Valid @RequestBody UserPasswordUpdateRequest request ) {
        Long userId = Long.valueOf(authentication.getName());
        userService.updatePassword(userId, request.getNewPassword(), request.getNewPasswordConfirm());
        return ApiResponse.success(200, "비밀번호가 성공적으로 변경되었습니다.", null);
    }
}
