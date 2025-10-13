package com.sian.community_api.dto.Comment;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class CommentPageResponse {
    private List<CommentResponse> content;
    private int totalPages;
    private long totalElements;
    private int size;
    private int number;
    private boolean first;
    private boolean last;

    public static CommentPageResponse from(Page<CommentResponse> commentPage) {
        return CommentPageResponse.builder()
                .content(commentPage.getContent())
                .totalPages(commentPage.getTotalPages())
                .totalElements(commentPage.getTotalElements())
                .size(commentPage.getSize())
                .number(commentPage.getNumber())
                .first(commentPage.isFirst())
                .last(commentPage.isLast())
                .build();
    }
}
