package com.sian.community_api.service.post;

import com.sian.community_api.config.PostValidator;
import com.sian.community_api.config.UserValidator;
import com.sian.community_api.entity.Post;
import com.sian.community_api.entity.User;
import com.sian.community_api.dto.post.PostCreateRequest;
import com.sian.community_api.dto.post.PostDetailResponse;
import com.sian.community_api.dto.post.PostSummaryResponse;
import com.sian.community_api.dto.post.PostUpdateRequest;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.repository.LikeRepository;
import com.sian.community_api.repository.PostRepository;
import com.sian.community_api.service.CommentService;
import com.sian.community_api.service.FileStorageService;
import com.sian.community_api.service.post.sort.PostSortStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserValidator userValidator;
    private final PostValidator postValidator;
    private final Map<String, PostSortStrategy> sortStrategies;
    private final FileStorageService fileStorageService;
    private final LikeRepository likeRepository;
    private final CommentService commentService;

    @Transactional(readOnly = true)
    public Page<PostSummaryResponse> getPagedPosts(int page, int size, String sortField, String direction) {
        List<Post> allPosts = postRepository.findAll();

        PostSortStrategy strategy = sortStrategies.getOrDefault(
                sortField,
                sortStrategies.get("createdAt")
        );

        Comparator<Post> comparator = strategy.getComparator();

        if (direction.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

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

    public Post createPost(Long userId, String title, String content, MultipartFile image) {

        User author = userValidator.findValidUserById(userId);

        postValidator.validateContent(title, content);

        String imagePath = null;
        if (image != null && !image.isEmpty()) {
            imagePath = fileStorageService.save(image); // 예: /uploads/posts/123.png
        }

        Post post = Post.builder()
                .author(author)
                .title(title != null ? title.trim() : null)
                .content(content)
                .postImage(imagePath)
                .likeCount(0)
                .viewCount(0)
                .commentCount(0)
                .build();

        return postRepository.save(post);
    }


    @Transactional
    public Post updatePost(Long postId, Long userId, String title, String content, MultipartFile image) {

        Post post = postValidator.findValidPostById(postId);
        userValidator.findValidUserById(userId);

        if (!post.getAuthor().getId().equals(userId)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "FORBIDDEN", "작성자만 게시글을 수정할 수 있습니다.");
        }

        if (title != null) {
            if (title.isBlank()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "invalid_title", "제목을 입력해주세요.");
            }
            post.updateTitle(title.trim());
        }

        if (content != null) {
            if (content.isBlank()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "invalid_content", "본문을 1자 이상 입력해주세요.");
            }
            post.updateContent(content);
        }

        if (image != null && !image.isEmpty()) {
            String imagePath = fileStorageService.save(image); // /uploads/...
            post.updatePostImage(imagePath);
        }

        postValidator.validateContent(post.getTitle(), post.getContent());

        return post;
    }


    @Transactional
    public PostDetailResponse getPostDetail(Long postId, Long userId) {

        Post post = postValidator.findValidPostById(postId);

        post.incrementViewCount();
        postRepository.save(post);

        boolean liked = false;
        if (userId != null) {
            User user = userValidator.findValidUserById(userId);
            liked = likeRepository.existsByPostAndUser(post, user);
        }

        return PostDetailResponse.from(post, userId, liked);
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postValidator.findValidPostById(postId);
        userValidator.findValidUserById(userId);

        if (!post.getAuthor().getId().equals(userId)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "FORBIDDEN", "작성자만 게시글을 삭제할 수 있습니다.");
        }

        commentService.deleteCommentsByPost(postId);
        postRepository.delete(post);
    }

}
