package com.sian.community_api.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sian.community_api.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostSummaryResponse {
    private Long id;
    private String title;
    private int likeCount;
    private int commentCount;
    private int viewCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private String authorNickname;
    private String authorProfileImage;

    public static PostSummaryResponse from(Post post) {

        String profileUrl = null;
        if (post.getAuthor().getProfileImage() != null) {
            profileUrl = "http://localhost:8080" + post.getAuthor().getProfileImage();
        }

        return PostSummaryResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .likeCount(post.getLikeCount())
                .viewCount(post.getViewCount())
                .commentCount(post.getCommentCount())
                .createdAt(post.getCreatedAt())
                .authorNickname(post.getAuthor().getNickname())
                .authorProfileImage(profileUrl)
                .build();
    }

}
