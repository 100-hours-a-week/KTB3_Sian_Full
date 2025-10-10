package com.sian.community_api.dto.user;

import com.sian.community_api.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignupResponse {
    private Long id;
    private String email;
    private String nickname;
    private String profileImage;

    public static UserSignupResponse from(User user) {
        return UserSignupResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .build();
    }
}
