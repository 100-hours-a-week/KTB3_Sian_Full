package com.sian.community_api.repository;

import com.sian.community_api.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    List<Comment> findByPostId(Long postId);
    void deleteByPostId(Long postId);
    Optional<Comment> findById(Long id);
    void delete(Long id);
}