package com.sian.community_api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    private Long id;
    private Long postId;
    private User author;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();

    private static final AtomicLong sequence = new AtomicLong(0);

    @Builder
    public Comment(Long postId, User author, String content) {
        this.id = sequence.incrementAndGet();
        this.postId = postId;
        this.author = author;
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
