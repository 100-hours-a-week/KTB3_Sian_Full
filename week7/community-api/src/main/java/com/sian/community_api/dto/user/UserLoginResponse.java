package com.sian.community_api.dto.user;

import com.sian.community_api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {
    private String accessToken;
    private Long id;
    private String email;
    private String nickname;
    private String profileImageUrl;

    public static UserLoginResponse of(User user, String accessToken) {
        String fullUrl = null;
        if (user.getProfileImage() != null) {
            fullUrl = "http://localhost:8080" + user.getProfileImage();
        }

        return new UserLoginResponse(
                accessToken,
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                fullUrl
        );
    }
}

