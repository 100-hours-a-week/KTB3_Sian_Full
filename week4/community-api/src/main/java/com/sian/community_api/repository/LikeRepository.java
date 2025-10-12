package com.sian.community_api.repository;

import com.sian.community_api.domain.Like;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class LikeRepository extends BaseRepository<Like> {

    @Override
    protected Long getId(Like like) {
        return like.getId();
    }

    @Override
    protected void setId(Like like, Long id) {
        like = Like.builder()
                .id(id)
                .postId(like.getPostId())
                .userId(like.getUserId())
                .build();
        store.put(id, like);
    }

    public boolean hasUserLiked(Long postId, Long userId) {
        return store.values().stream()
                .anyMatch(like -> like.getPostId().equals(postId) && like.getUserId().equals(userId));
    }

    public void hasUserDeletedLike(Long postId, Long userId) {
        Long targetId = store.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .filter(entry -> postId.equals(entry.getValue().getPostId())
                        && userId.equals(entry.getValue().getUserId()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        if (targetId != null) {
            store.remove(targetId);
        }
    }
    public List<Like> findByPostId(Long postId) {
        return store.values().stream()
                .filter(like -> like.getPostId().equals(postId))
                .collect(Collectors.toList());
    }

}
