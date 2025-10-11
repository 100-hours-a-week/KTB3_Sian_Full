package com.sian.community_api.dto.user;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {

    @Pattern(regexp = "^[^\\s]{1,10}$", message = "닉네임은 공백 없이 1~10자 이내여야 합니다.")
    private String nickname;

    private String profileImage;
}
