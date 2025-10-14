package com.sian.community_api.controller;

import com.sian.community_api.config.AuthUtil;
import com.sian.community_api.domain.Comment;
import com.sian.community_api.dto.Comment.CommentRequest;
import com.sian.community_api.dto.Comment.CommentPageResponse;
import com.sian.community_api.dto.Comment.CommentResponse;
import com.sian.community_api.dto.common.ApiResponse;
import com.sian.community_api.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final AuthUtil authUtill;

    @GetMapping
    public ApiResponse<CommentPageResponse> getComments(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        String userEmail = authUtill.extractEmail(authHeader);
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        String direction = sortParams.length > 1 ? sortParams[1] : "desc";

        Page<CommentResponse> commentPage = commentService.getCommentsByPost(postId, userEmail, page, size, sortField, direction);
        CommentPageResponse response = CommentPageResponse.from(commentPage);

        return ApiResponse.success(200, "fetch_comments_success", response);
    }

    @PostMapping
    public ApiResponse<CommentResponse> createComment(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest request
    ) {
        String userEmail = authUtill.extractEmail(authHeader);

        Comment comment = commentService.createComment(postId, userEmail, request.getContent());

        return ApiResponse.created(CommentResponse.from(comment, userEmail));
    }

    @PutMapping("/{commentId}")
    public ApiResponse<CommentResponse> updateComment(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest request
    ) {
        String userEmail = authUtill.extractEmail(authHeader);
        Comment updated = commentService.updateComment(commentId, userEmail, request.getContent());
        return ApiResponse.ok(CommentResponse.from(updated, userEmail));
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        String userEmail = authUtill.extractEmail(authHeader);
        commentService.deleteComment(commentId, userEmail);
        return ApiResponse.success(200, "댓글이 성공적으로 삭제되었습니다.", null);
    }
}
