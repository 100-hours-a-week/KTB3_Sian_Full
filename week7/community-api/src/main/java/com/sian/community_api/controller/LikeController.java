package com.sian.community_api.controller;

import com.sian.community_api.config.AuthUtil;
import com.sian.community_api.dto.common.ApiResponse;
import com.sian.community_api.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final AuthUtil authUtil;

    @PostMapping("/{postId}/likes")
    public ApiResponse<Void> addLike(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long postId
    ) {
        Long userId = authUtil.extractUserId(authHeader);
        likeService.addLike(postId, userId);
        return ApiResponse.success(200, "like_added_success", null);
    }

    @DeleteMapping("/{postId}/likes")
    public ApiResponse<Void> removeLike(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long postId
    ) {
        Long userId = authUtil.extractUserId(authHeader);
        likeService.removeLike(postId, userId);
        return ApiResponse.success(200, "like_removed_success", null);
    }
}
