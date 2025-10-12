package com.sian.community_api.service;

import com.sian.community_api.domain.Post;
import com.sian.community_api.domain.User;
import com.sian.community_api.dto.post.PostCreateRequest;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.repository.PostRepository;
import com.sian.community_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시글 전체 조회
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // 게시글 id 조회
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "해당 게시글을 찾을 수 없습니다."));
    }

    // 게시글 작성
    public Post createPost(String userEmail, PostCreateRequest request) {

        User author = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "작성자를 찾을 수 없습니다."));

        String title = request.getTitle();
        String content = request.getContent();

        // 둘 다 비었을 때
        if ((title == null || title.isBlank()) && (content == null || content.isBlank())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid_input_value", "제목과 내용을 모두 작성해주세요.");
        }

        // 제목이 null이거나 ""인 경우
        if (title == null || title.isBlank()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid_input_value", "제목을 입력해주세요.");
        }

        // 내용이 null이거나 ""인 경우
        if (content == null || content.isBlank()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid_input_value", "내용을 입력해주세요.");
        }

        // 제목 26자 초과한 경우
        if (title != null && title.length() > 26) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid_input_value", "제목은 최대 26자까지 작성 가능합니다.");
        }

        Post post = Post.builder()
                .author(author)
                .title(title != null ? title.trim() : null)
                .content(content)
                .postImage(request.getPostImage())
                .likeCount(0)
                .viewCount(0)
                .commentCount(0)
                .build();

        return postRepository.save(post);
    }
}
