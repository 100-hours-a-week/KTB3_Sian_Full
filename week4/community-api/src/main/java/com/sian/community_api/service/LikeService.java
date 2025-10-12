package com.sian.community_api.service;

import com.sian.community_api.domain.Like;
import com.sian.community_api.domain.Post;
import com.sian.community_api.domain.User;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.repository.LikeRepository;
import com.sian.community_api.repository.PostRepository;
import com.sian.community_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND,
                        "user_not_found",
                        "사용자를 찾을 수 없습니다."
                ));
    }

    private Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "게시글을 찾을 수 없습니다."));
    }

    public void addLike(Long postId, String userEmail) {
        Post post = getPostById(postId);

        User user = getUserByEmail(userEmail);

        if (likeRepository.hasUserLiked(postId, user.getId())) {
            throw new CustomException(HttpStatus.CONFLICT, "ALREADY_LIKED", "이미 좋아요를 누른 게시글입니다.");
        }

        likeRepository.save(Like.builder()
                .postId(postId)
                .userId(user.getId())
                .build());

        post.incrementLike();
        postRepository.save(post);
    }

    public void removeLike(Long postId, String userEmail) {

        Post post = getPostById(postId);
        User user = getUserByEmail(userEmail);

        if (!likeRepository.hasUserLiked(postId, user.getId())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "NOT_LIKED", "좋아요를 누르지 않은 게시글입니다.");
        }

        likeRepository.hasUserDeletedLike(postId, user.getId());
        post.decrementLike();
        postRepository.save(post);
    }
}
