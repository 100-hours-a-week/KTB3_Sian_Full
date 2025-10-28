package com.sian.community_api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserSignupRequest {
    @Email (message = "올바른 이메일 주소 형식을 입력해주세요.")
    @NotBlank (message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank (message = "비밀번호를 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9\\s]).{8,20}$",
            message = "비밀번호는 8~20자, 대문자·소문자·숫자·특수문자를 각각 1개 이상 포함해야 합니다."
    )
    private String password;

    @NotBlank (message = "비밀번호를 한번 더 입력해주세요.")
    private String passwordConfirm;

    @NotBlank (message = "닉네임을 입력해주세요.")
    @Size (max = 10, message = "닉네임은 최대 10자까지 작성 가능합니다.")
    @Pattern(
            regexp = "^[A-Za-z0-9가-힣]+$",
            message = "띄어쓰기를 없애주세요."
    )
    private String nickname;

    @NotBlank (message = "프로필 사진을 추가해주세요.")
    private String profileImage;

    public boolean isPasswordConfirmed() {
        return password != null && password.equals(passwordConfirm);
    }

}
