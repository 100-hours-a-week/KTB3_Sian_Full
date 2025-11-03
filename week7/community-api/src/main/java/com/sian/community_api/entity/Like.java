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
@SequenceGenerator(name = "like_seq_generator", sequenceName = "like_seq", allocationSize = 1)
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_seq_generator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
