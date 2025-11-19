package com.sian.community_api.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sian.community_api.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDetailResponse {
    private Long id;
    private String title;
    private String content;
    private String postImageUrl;
    private int likeCount;
    private int viewCount;
    private int commentCount;
    @JsonProperty("isAuthor")
    private boolean isAuthor;
    private boolean liked;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private String authorNickname;
    private String authorProfileImage;

    public static PostDetailResponse from(Post post, Long userId) {
        return from(post, userId, false);
    }

    public static PostDetailResponse from(Post post, Long userId, boolean liked) {

        boolean isAuthor = userId != null && post.getAuthor().getId().equals(userId);

        String fullImageUrl = null;
        if (post.getPostImage() != null) {
            fullImageUrl = "http://localhost:8080" + post.getPostImage();
        }

        String authorProfileUrl = null;
        if (post.getAuthor().getProfileImage() != null) {
            authorProfileUrl = "http://localhost:8080" + post.getAuthor().getProfileImage();
        }

        return PostDetailResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .postImageUrl(fullImageUrl)
                .likeCount(post.getLikeCount())
                .viewCount(post.getViewCount())
                .commentCount(post.getCommentCount())
                .createdAt(post.getCreatedAt())
                .authorNickname(post.getAuthor().getNickname())
                .authorProfileImage(authorProfileUrl)
                .isAuthor(isAuthor)
                .liked(liked)
                .build();
    }
}
