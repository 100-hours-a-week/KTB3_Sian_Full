package com.sian.community_api.repository;

import com.sian.community_api.entity.Like;
import com.sian.community_api.entity.Post;
import com.sian.community_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByPostAndUser(Post post, User user);
    void deleteByPostAndUser(Post post, User user);
}
