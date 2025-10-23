package com.sian.community_api.repository;

import com.sian.community_api.domain.Like;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class LikeRepositoryImpl implements LikeRepository {
    private static final Set<Like> store = new HashSet<>();

    @Override
    public boolean hasUserLiked(Long postId, Long userId) {
        return store.stream()
                .anyMatch(like -> like.getPostId().equals(postId)
                        && like.getUserId().equals(userId));
    }

    @Override
    public void save(Like like) {
        store.add(like);
    }

    @Override
    public void delete(Long postId, Long userId) {
        store.removeIf(like -> like.getPostId().equals(postId)
                && like.getUserId().equals(userId));
    }
}