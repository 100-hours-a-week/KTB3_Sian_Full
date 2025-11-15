package com.sian.community_api.dto.user;

import com.sian.community_api.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponse {
    private String accessToken;
    private String refreshToken;
    private Long id;
    private String email;
    private String nickname;
    private String profileImageUrl;

    public static UserLoginResponse of(User user, String accessToken, String refreshToken) {

        String fullUrl = null;
        if (user.getProfileImage() != null) {
            fullUrl = "http://localhost:8080" + user.getProfileImage();
        }

        return UserLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImageUrl(fullUrl)
                .build();
    }
}

