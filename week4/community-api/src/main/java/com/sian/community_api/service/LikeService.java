package com.sian.community_api.service;

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

    private Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "게시글을 찾을 수 없습니다."));
    }

    public void addLike(Long postId, String userEmail) {
        Post post = getPostById(postId);
        User user = userValidator.findValidUser(userEmail);

        if (likeRepository.hasUserLiked(postId, user.getId())) {
            throw new CustomException(HttpStatus.CONFLICT, "ALREADY_LIKED", "이미 좋아요를 누른 게시글입니다.");
        }

        likeRepository.save(new Like(user.getId(), postId));

        post.incrementLike();
        postRepository.save(post);
    }

    public void removeLike(Long postId, String userEmail) {

        Post post = getPostById(postId);
        User user = userValidator.findValidUser(userEmail);

        if (!likeRepository.hasUserLiked(postId, user.getId())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "NOT_LIKED", "좋아요를 누르지 않은 게시글입니다.");
        }

        likeRepository.delete(postId, user.getId());
        post.decrementLike();
        postRepository.save(post);
    }
}
