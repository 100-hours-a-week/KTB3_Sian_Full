package com.sian.community_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id","post_id"})
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne // 한 명의 유저는 여러 개의 좋아요
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne // 하나의 게시글은 여러 개의 좋아요
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
