package com.sian.community_api.controller;

import com.sian.community_api.dto.common.ApiResponse;
import com.sian.community_api.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{postId}/likes")
    public ApiResponse<Void> addLike(
            Authentication authentication,
            @PathVariable Long postId
    ) {
        Long userId = Long.valueOf(authentication.getName());
        likeService.addLike(postId, userId);
        return ApiResponse.success(200, "like_added_success", null);
    }

    @DeleteMapping("/{postId}/likes")
    public ApiResponse<Void> removeLike(
            Authentication authentication,
            @PathVariable Long postId
    ) {
        Long userId = Long.valueOf(authentication.getName());
        likeService.removeLike(postId, userId);
        return ApiResponse.success(200, "like_removed_success", null);
    }
}
