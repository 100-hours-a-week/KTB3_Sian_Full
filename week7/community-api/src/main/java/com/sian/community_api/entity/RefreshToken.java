package com.sian.community_api.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "refreshToken")
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "refresh_token_seq_generator", sequenceName = "refresh_token_seq", allocationSize = 1)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_seq_generator")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String token;
    private LocalDateTime expireAt = LocalDateTime.now().plusDays(7);

    @Builder
    public RefreshToken(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public void updateExpireAt() {
        this.expireAt = LocalDateTime.now().plusDays(7);
    }

    public void updateToken(String token) {
        this.token =token;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireAt);
    }

}
