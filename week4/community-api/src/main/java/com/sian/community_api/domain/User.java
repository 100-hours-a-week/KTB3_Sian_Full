package com.sian.community_api.domain;
import lombok.*;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private boolean isDeleted = false;
    private String profileImage;

    private static final AtomicLong sequence = new AtomicLong(0);

    @Builder
    public User(String email, String nickname, String password, String profileImage) {
        this.id = sequence.incrementAndGet();
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.profileImage = profileImage;
        this.isDeleted = false;
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
