package com.sian.community_api.dto.user;

import com.sian.community_api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginTokens {
    private User user;
    private String accessToken;
    private String refreshToken;
}