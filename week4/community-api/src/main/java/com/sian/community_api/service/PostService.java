package com.sian.community_api.service;

import com.sian.community_api.config.UserValidator;
import com.sian.community_api.domain.Post.Post;
import com.sian.community_api.domain.User.User;
import com.sian.community_api.dto.post.PostCreateRequest;
import com.sian.community_api.dto.post.PostSummaryResponse;
import com.sian.community_api.dto.post.PostUpdateRequest;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.domain.Post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserValidator userValidator;

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "해당 게시글을 찾을 수 없습니다."));
    }

    private void validatePostContent(String title, String content) {
        if ((title == null || title.isBlank()) || (content == null || content.isBlank())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid_input_value", "제목과 내용을 모두 작성해주세요.");
        }

        if (title != null && title.length() > 26) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid_input_value", "제목은 최대 26자까지 작성 가능합니다.");
        }
    }

    public Page<PostSummaryResponse> getPagedPosts(int page, int size, String sortField, String direction) {
        List<Post> allPosts = postRepository.findAll();

        Comparator<Post> comparator;
        switch (sortField) {
            case "likes" -> comparator = Comparator.comparing(Post::getLikeCount);
            case "views" -> comparator = Comparator.comparing(Post::getViewCount);
            default -> comparator = Comparator.comparing(Post::getCreatedAt);
        }

        if (direction.equalsIgnoreCase("desc")) comparator = comparator.reversed();
        allPosts.sort(comparator);

        int start = page * size;
        int end = Math.min(start + size, allPosts.size());
        if (start > allPosts.size()) {
            return new PageImpl<>(List.of(), PageRequest.of(page, size), allPosts.size());
        }

        List<PostSummaryResponse> content = allPosts.subList(start, end).stream()
                .map(PostSummaryResponse::from)
                .toList();

        return new PageImpl<>(content, PageRequest.of(page, size), allPosts.size());
    }

    public Post createPost(Long userId, PostCreateRequest request) {

        User author = userValidator.findValidUserById(userId);
        String title = request.getTitle();
        String content = request.getContent();

        validatePostContent(title, content);

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

    public Post updatePost(Long postId, Long userId, PostUpdateRequest request) {
        Post post = getPostById(postId);
        userValidator.findValidUserById(userId);

        if (!post.getAuthor().getId().equals(userId)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "FORBIDDEN", "작성자만 게시글을 수정할 수 있습니다.");
        }

        if (request.getTitle() != null) {
            if (request.getTitle().isBlank()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "invalid_title", "제목을 입력해주세요.");
            }
            post.setTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            if (request.getContent().isBlank()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "invalid_content", "본문을 1자 이상 입력해주세요.");
            }
            post.setContent(request.getContent());
        }


        if (request.getPostImage() != null && !request.getPostImage().isBlank()) {
            post.setPostImage(request.getPostImage());
        }

        validatePostContent(post.getTitle(), post.getContent());

        return post;
    }

     public void deletePost(Long postId, Long userId) {
        Post post = getPostById(postId);
        userValidator.findValidUserById(userId);

        if (!post.getAuthor().getId().equals(userId)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "FORBIDDEN", "작성자만 게시글을 삭제할 수 있습니다.");
        }

        postRepository.delete(postId);
    }
}
