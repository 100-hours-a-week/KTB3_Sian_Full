package com.sian.community_api.controller;

import com.sian.community_api.config.AuthUtil;
import com.sian.community_api.domain.Post;
import com.sian.community_api.dto.common.ApiResponse;
import com.sian.community_api.dto.post.*;
import com.sian.community_api.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
@Validated
public class PostController {

    private final PostService postService;
    private final AuthUtil authUtil;

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

    @GetMapping("/{id}")
    public ApiResponse<PostDetailResponse> getPostById(@RequestHeader(value = "Authorization", required = false) String authHeader, @PathVariable Long id) {
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                email = authUtil.extractEmail(authHeader);
            } catch (Exception e) {
                email = null;
            }
        }
        Post post = postService.getPostById(id);
        PostDetailResponse response = PostDetailResponse.from(post, email);
        return ApiResponse.ok(response);
    }

    @PostMapping
    public ApiResponse<PostDetailResponse> createPost(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody PostCreateRequest request
    ) {
        String userEmail = authUtil.extractEmail(authHeader);
        Post createdPost = postService.createPost(userEmail, request);
        return ApiResponse.created(PostDetailResponse.from(createdPost,userEmail));
    }

    @PatchMapping("/{id}")
    public ApiResponse<PostDetailResponse> updatePost(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("id") Long postId,
            @RequestBody PostUpdateRequest request
    ) {
        String userEmail = authUtil.extractEmail(authHeader);
        Post updatedPost = postService.updatePost(postId, userEmail, request);
        return ApiResponse.ok(PostDetailResponse.from(updatedPost, userEmail));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePost(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("id") Long postId
    ) {
        String userEmail = authUtil.extractEmail(authHeader);
        postService.deletePost(postId, userEmail);
        return ApiResponse.success(200, "게시글이 삭제되었습니다.", null);
    }
}
