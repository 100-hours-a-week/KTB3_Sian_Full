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
public class Post {
    private Long id;
    private User author;
    private String title;
    private String content;
    private String postImage;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private Integer likeCount;
    private Integer viewCount;
    private Integer commentCount;

    private static final AtomicLong sequence = new AtomicLong(0);

    @Builder
    public Post(User author, String title, String content, String postImage, Integer likeCount, Integer viewCount, Integer commentCount) {
        this.id = sequence.incrementAndGet();
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.likeCount = (likeCount != null) ? likeCount : 0;
        this.viewCount = (viewCount != null) ? viewCount : 0;
        this.commentCount = (commentCount != null) ? commentCount : 0;
        if(postImage != null) { this.postImage = postImage; }
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updatePostImage(String postImage) {
        this.postImage = postImage;
    }

    public void incrementLike() {
        this.likeCount++;
    }

    public void decrementLike() {
        if (this.likeCount > 0 ) this.likeCount--;
    }
}
