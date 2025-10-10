package com.sian.community_api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserSignupRequest {
    @Email (message = "올바른 이메일 주소 형식을 입력해주세요.")
    @NotBlank (message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank (message = "비밀번호를 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 8자 이상, 20자 이하이며, 대문자,소문자,숫자,특수문자를 각각 최소 1개 포함해야 합니다."
    )
    private String password;

    @NotBlank (message = "비밀번호를 한번더 입력해주세요.")
    private String passwordConfirm;

    @NotBlank (message = "닉네임을 입력해주세요.")
    private String nickname;

    private String profileImage;

    // 비밀번호 확인
    public boolean isPasswordConfirmed() {
        return password != null && password.equals(passwordConfirm);
    }

}
