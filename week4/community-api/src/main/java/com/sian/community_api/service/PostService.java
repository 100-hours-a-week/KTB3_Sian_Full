package com.sian.community_api.service;

import com.sian.community_api.domain.Post;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 전체 조회
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // id 조회
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "해당 게시글을 찾을 수 없습니다."));
    }
}
