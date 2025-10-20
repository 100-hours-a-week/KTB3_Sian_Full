package com.sian.community_api.domain.Like;

public interface LikeRepository {
    void save(Like like);
    boolean hasUserLiked(Long postId, Long userId);
    void delete(Long postId, Long userId);
}
