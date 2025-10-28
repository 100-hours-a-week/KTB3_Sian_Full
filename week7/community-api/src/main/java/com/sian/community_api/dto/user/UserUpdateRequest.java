package com.sian.community_api.dto.user;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {

    @Pattern(regexp = "^[^\\s]{1,10}$", message = "닉네임은 최대 10자까지 작성 가능합니다.")
    private String nickname;

    private String profileImage;
}
