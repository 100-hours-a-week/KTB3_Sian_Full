package com.sian.community_api.repository;

import com.sian.community_api.domain.Like;
import com.sian.community_api.domain.Post;
import com.sian.community_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByPostIdAndUserId(User user, Post post);
    void deleteByPostIdAndUserId(User user, Post post);
}
