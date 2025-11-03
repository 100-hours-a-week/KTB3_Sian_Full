package com.sian.community_api.config;

import com.sian.community_api.entity.Post;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostValidator {
    private final PostRepository postRepository;

    public Post findValidPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "해당 게시글을 찾을 수 없습니다."));
    }

    public void validateContent(String title, String content) {
        if ((title == null || title.isBlank()) || (content == null || content.isBlank())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "INVALID_INPUT_VALUE", "제목과 내용을 모두 작성해주세요.");
        }

        if (title != null && title.length() > 26) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "INVALID_TITLE_LENGTH", "제목은 최대 26자까지 작성 가능합니다.");
        }
    }
}
