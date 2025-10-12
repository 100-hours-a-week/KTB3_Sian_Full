package com.sian.community_api.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sian.community_api.domain.Comment;
import com.sian.community_api.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
public class PostDetailResponse {
    private Long id;
    private String title;
    private String content;
    private String postImage;
    private int likeCount;
    private int viewCount;
    private int commentCount;
    private boolean isAuthor;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private String authorNickname;
    private String authorProfileImage;

    private Set<Comment> comments; // CommentResponseDto로 교체 예정

    public static PostDetailResponse from(Post post, String currentUserEmail) {

        boolean isAuthor = post.getAuthor().getEmail().equals(currentUserEmail);

        return PostDetailResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .postImage(post.getPostImage())
                .likeCount(post.getLikeCount())
                .viewCount(post.getViewCount())
                .commentCount(post.getCommentCount())
                .createdAt(post.getCreatedAt())
                .authorNickname(post.getAuthor().getNickname())
                .authorProfileImage(post.getAuthor().getProfileImage())
                .comments(post.getComments())
                .isAuthor(isAuthor)
                .build();
    }

}
