package com.sian.community_api.dto.Comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sian.community_api.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long id;
    private Long postId;
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private String authorNickname;
    private String authorProfileImage;
    private boolean isAuthor;

    public static CommentResponse from(Comment comment, Long userId) {

        boolean isAuthor = comment.getAuthor().getId().equals(userId);

        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .authorNickname(comment.getAuthor().getNickname())
                .authorProfileImage(comment.getAuthor().getProfileImage())
                .isAuthor(isAuthor)
                .build();
    }
}
