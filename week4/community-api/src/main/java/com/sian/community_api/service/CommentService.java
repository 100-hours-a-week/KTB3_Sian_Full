package com.sian.community_api.service;

import com.sian.community_api.domain.Comment;
import com.sian.community_api.domain.Post;
import com.sian.community_api.domain.User;
import com.sian.community_api.dto.Comment.CommentResponse;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.repository.CommentRepository;
import com.sian.community_api.repository.PostRepository;
import com.sian.community_api.repository.UserRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "게시글을 찾을 수 없습니다."));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "사용자를 찾을 수 없습니다."));
    }

    public Comment createComment(Long postId, String userEmail, String content) {
        Post post = getPostById(postId);
        User author = getUserByEmail(userEmail);

        if (content == null || content.isBlank()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "INVALID_COMMENT", "댓글 내용을 입력해주세요.");
        }

        Comment comment = Comment.builder()
                .postId(postId)
                .author(author)
                .content(content.trim())

                .build();

        commentRepository.save(comment); // 댓글수 증가 구현 전
        return comment;
    }

    // 댓글 목록 조회
    public Page<CommentResponse> getCommentsByPost(Long postId, String userEmail, int page, int size, String sortField, String direction) {

        List<Comment> allComments = commentRepository.findByPostId(postId);

            Comparator<Comment> comparator = switch (sortField) {
            case "createdAt" -> Comparator.comparing(Comment::getCreatedAt);
            default -> Comparator.comparing(Comment::getId);
        };

        if (direction.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        allComments.sort(comparator);

        int start = page * size;
        int end = Math.min(start + size, allComments.size());
        List<CommentResponse> content = allComments.subList(start, end).stream()
                .map(comment -> CommentResponse.from(comment, userEmail))
                .toList();

        return new PageImpl<>(content, PageRequest.of(page, size), allComments.size());
    }

    public Comment updateComment(Long commentId, String userEmail, String newContent) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "COMMENT_NOT_FOUND", "댓글을 찾을 수 없습니다."));

        if (!comment.getAuthor().getEmail().equals(userEmail)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "FORBIDDEN", "작성자만 댓글을 수정할 수 있습니다.");
        }

        if (newContent == null || newContent.isBlank()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "INVALID_COMMENT", "댓글 내용을 입력해주세요.");
        }

        comment.updateContent(newContent.trim());
        commentRepository.save(comment);

        return comment;
    }

}
