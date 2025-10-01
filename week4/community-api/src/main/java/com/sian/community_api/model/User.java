package com.sian.community_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private boolean isDeleted = false;

    @JsonProperty("profile_image")
    private String profileImage;
}
