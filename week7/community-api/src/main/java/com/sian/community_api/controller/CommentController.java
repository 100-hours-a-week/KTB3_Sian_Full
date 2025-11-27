package com.sian.community_api.controller;

import com.sian.community_api.entity.Comment;
import com.sian.community_api.dto.Comment.CommentRequest;
import com.sian.community_api.dto.Comment.CommentPageResponse;
import com.sian.community_api.dto.Comment.CommentResponse;
import com.sian.community_api.dto.common.ApiResponse;
import com.sian.community_api.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ApiResponse<CommentPageResponse> getComments(
            Authentication authentication,
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Positive int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        Long userId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            userId = Long.valueOf(authentication.getName());
        }

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        String direction = sortParams.length > 1 ? sortParams[1] : "desc";

        Page<CommentResponse> commentPage = commentService.getCommentsByPost(postId, userId, page, size, sortField, direction);
        CommentPageResponse response = CommentPageResponse.from(commentPage);

        return ApiResponse.ok(response);
    }

    @PostMapping
    public ApiResponse<CommentResponse> createComment(
            Authentication authentication,
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest request
    ) {
        Long userId = Long.valueOf(authentication.getName());
        Comment comment = commentService.createComment(postId, userId, request.getContent());
        return ApiResponse.created(CommentResponse.from(comment, userId));
    }

    @PutMapping("/{commentId}")
    public ApiResponse<CommentResponse> updateComment(
            Authentication authentication,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest request
    ) {
        Long userId = Long.valueOf(authentication.getName());
        Comment updated = commentService.updateComment(commentId, userId, request.getContent());
        return ApiResponse.ok(CommentResponse.from(updated, userId));
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(
            Authentication authentication,
            @PathVariable Long commentId
    ) {
        Long userId = Long.valueOf(authentication.getName());
        commentService.deleteComment(commentId, userId);
        return ApiResponse.success(200, "댓글이 성공적으로 삭제되었습니다.", null);
    }
}
