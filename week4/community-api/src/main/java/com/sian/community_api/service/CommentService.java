package com.sian.community_api.service;

import com.sian.community_api.config.UserValidator;
import com.sian.community_api.domain.Comment;
import com.sian.community_api.domain.User;
import com.sian.community_api.dto.Comment.CommentResponse;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.repository.CommentRepository;
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
    private final UserValidator userValidator;

    public Comment createComment(Long postId, String email, String content) {

        User author = userValidator.findValidUser(email);

        if (content == null || content.isBlank()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "INVALID_COMMENT", "댓글 내용을 입력해주세요.");
        }

        Comment comment = Comment.builder()
                .postId(postId)
                .author(author)
                .content(content.trim())
                .build();

        commentRepository.save(comment);
        return comment;
    }

    // 댓글 목록 조회 + 페이징
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

        userValidator.findValidUser(userEmail); // 탈퇴 여부 검사
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

    public void deleteComment(Long commentId, String userEmail) {
        userValidator.findValidUser(userEmail); // 탈퇴 여부 검사
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "COMMENT_NOT_FOUND", "댓글을 찾을 수 없습니다."));

        if (!comment.getAuthor().getEmail().equals(userEmail)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "FORBIDDEN", "작성자만 댓글을 삭제할 수 있습니다.");
        }

        commentRepository.delete(commentId);
    }

}
