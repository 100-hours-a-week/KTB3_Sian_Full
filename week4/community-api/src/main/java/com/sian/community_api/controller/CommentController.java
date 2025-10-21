package com.sian.community_api.controller;

import com.sian.community_api.config.AuthUtil;
import com.sian.community_api.domain.Comment.Comment;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;
    private final AuthUtil authUtill;

    @GetMapping
    public ApiResponse<CommentPageResponse> getComments(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Positive int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        Long userId = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                userId = authUtill.extractUserId(authHeader);
            } catch (Exception ignored) {
                userId = null;
            }
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
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest request
    ) {
        Long userId = authUtill.extractUserId(authHeader);
        Comment comment = commentService.createComment(postId, userId, request.getContent());
        return ApiResponse.created(CommentResponse.from(comment, userId));
    }

    @PutMapping("/{commentId}")
    public ApiResponse<CommentResponse> updateComment(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest request
    ) {
        Long userId = authUtill.extractUserId(authHeader);
        Comment updated = commentService.updateComment(commentId, userId, request.getContent());
        return ApiResponse.ok(CommentResponse.from(updated, userId));
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long commentId
    ) {
        Long userId = authUtill.extractUserId(authHeader);
        commentService.deleteComment(commentId, userId);
        return ApiResponse.success(200, "댓글이 성공적으로 삭제되었습니다.", null);
    }
}
