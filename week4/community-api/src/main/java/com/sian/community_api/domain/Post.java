package com.sian.community_api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private Long id;
    private User author;
    private String title;
    private String content; // 게시글 텍스트 내용
    private String postImage;

    @Builder.Default
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now(); // 2021-01-01 00:00:00 형태

    private int likeCount; // 좋아요수
    private int viewCount; // 조회수

    @Builder.Default
    private Set<Comment> comments = new HashSet<>(); // 댓글 리스트
    @Builder.Default
    private int commentCount = 0; // 댓글 수

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }
}
