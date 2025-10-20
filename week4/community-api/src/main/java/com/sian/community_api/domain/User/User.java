package com.sian.community_api.domain.User;
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
    @Builder.Default
    private boolean isDeleted = false;
    private String profileImage;

    public void setId(Long id) {
        this.id = id;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
