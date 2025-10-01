package com.sian.community_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Set;

public class Post {
    private Long id;
    private User author; // author id
    private String title;
    private String content; // 게시글 텍스트 내용

    @JsonProperty("post_image")
    private String postImage;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt; // 2021-01-01 00:00:00 형태

    private Set<Like> likes; // 좋아요수
    private int views; // 조회수
    private int comments;
}
