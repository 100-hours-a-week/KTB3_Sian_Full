package com.sian.community_api.controller;

import com.sian.community_api.auth.AuthUtil;
import com.sian.community_api.domain.Post;
import com.sian.community_api.dto.common.ApiResponse;
import com.sian.community_api.dto.post.PostCreateRequest;
import com.sian.community_api.dto.post.PostDetailResponse;
import com.sian.community_api.dto.post.PostSummaryResponse;
import com.sian.community_api.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final AuthUtil authUtil;

    // 전체 게시글 조회
    @GetMapping
    public ApiResponse<List<PostSummaryResponse>> getAllPosts() {
        List<PostSummaryResponse> response = postService.getAllPosts().stream()
                .map(PostSummaryResponse::from)
                .collect(Collectors.toList());
        return ApiResponse.ok(response);
    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public ApiResponse<PostDetailResponse> getPostById(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {

        String email = authUtil.extractEmail(authHeader);
        Post post = postService.getPostById(id);
        PostDetailResponse response = PostDetailResponse.from(post, email);
        return ApiResponse.ok(response);
    }

    // 게시글 작성
    @PostMapping
    public ApiResponse<PostDetailResponse> createPost(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody PostCreateRequest request
    ) {
        String userEmail = authUtil.extractEmail(authHeader);
        Post createdPost = postService.createPost(userEmail, request);
        return ApiResponse.created(PostDetailResponse.from(createdPost,userEmail));
    }
}
