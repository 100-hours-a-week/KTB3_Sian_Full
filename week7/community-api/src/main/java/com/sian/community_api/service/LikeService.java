package com.sian.community_api.service;

import com.sian.community_api.config.PostValidator;
import com.sian.community_api.config.UserValidator;
import com.sian.community_api.domain.Like;
import com.sian.community_api.domain.Post;
import com.sian.community_api.domain.User;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.repository.LikeRepository;
import com.sian.community_api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserValidator userValidator;
    private final PostValidator postValidator;

    public void addLike(Long postId, Long userId) {
        Post post = postValidator.findValidPostById(postId);
        User user = userValidator.findValidUserById(userId);

        if (likeRepository.existsByPostIdAndUserId(user, post)) {
            throw new CustomException(HttpStatus.CONFLICT, "ALREADY_LIKED", "이미 좋아요를 누른 게시글입니다.");
        }

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        likeRepository.save(like);

        post.incrementLike();
        postRepository.save(post);
    }

    public void removeLike(Long postId, Long userId) {

        Post post = postValidator.findValidPostById(postId);
        User user = userValidator.findValidUserById(userId);

        if (!likeRepository.existsByPostIdAndUserId(user, post)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "NOT_LIKED", "좋아요를 누르지 않은 게시글입니다.");
        }

        likeRepository.deleteByPostIdAndUserId(user, post);
        post.decrementLike();
        postRepository.save(post);
    }
}
