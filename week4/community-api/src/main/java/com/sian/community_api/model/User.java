package com.sian.community_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private boolean isDeleted = false;
    private String profileImage;

    public void setId(Long id) {
        this.id = id;
    }
}
