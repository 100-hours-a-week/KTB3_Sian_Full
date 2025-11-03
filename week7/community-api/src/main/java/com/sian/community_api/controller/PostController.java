package com.sian.community_api.controller;

import com.sian.community_api.config.AuthUtil;
import com.sian.community_api.config.PostValidator;
import com.sian.community_api.entity.Post;
import com.sian.community_api.dto.common.ApiResponse;
import com.sian.community_api.dto.post.*;
import com.sian.community_api.service.post.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
@Validated
public class PostController {

    private final PostService postService;
    private final AuthUtil authUtil;
    private final PostValidator postValidator;

    @GetMapping
    public ApiResponse<PostPageResponse> getAllPosts(
            @RequestParam(defaultValue = "0") @Min(0) int page, // 현재 페이지
            @RequestParam(defaultValue = "10") @Positive int size,
            @RequestParam(defaultValue = "created_at,desc") String sort // 최신순
    ) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        String direction = sortParams.length > 1 ? sortParams[1] : "desc";

        Page<PostSummaryResponse> postPage = postService.getPagedPosts(page, size, sortField, direction);
        PostPageResponse response = PostPageResponse.from(postPage);

        return ApiResponse.ok(response);
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostDetailResponse> getPostById(@RequestHeader(value = "Authorization", required = false) String authHeader, @PathVariable Long postId) {
        Long userId = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                userId = authUtil.extractUserId(authHeader);
            } catch (Exception e) {
                userId = null;
            }
        }
        Post post = postValidator.findValidPostById(postId);
        PostDetailResponse response = postService.getPostDetail(postId, userId);
        return ApiResponse.ok(response);
    }

    @PostMapping
    public ApiResponse<PostDetailResponse> createPost(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody PostCreateRequest request
    ) {
        Long userId = authUtil.extractUserId(authHeader);
        Post createdPost = postService.createPost(userId, request);
        return ApiResponse.created(PostDetailResponse.from(createdPost,userId));
    }

    @PatchMapping("/{id}")
    public ApiResponse<PostDetailResponse> updatePost(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("id") Long postId,
            @RequestBody PostUpdateRequest request
    ) {
        Long userId = authUtil.extractUserId(authHeader);
        Post updatedPost = postService.updatePost(postId, userId, request);
        return ApiResponse.ok(PostDetailResponse.from(updatedPost, userId));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePost(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("id") Long postId
    ) {
        Long userId = authUtil.extractUserId(authHeader);
        postService.deletePost(postId, userId);
        return ApiResponse.success(200, "게시글이 삭제되었습니다.", null);
    }
}
