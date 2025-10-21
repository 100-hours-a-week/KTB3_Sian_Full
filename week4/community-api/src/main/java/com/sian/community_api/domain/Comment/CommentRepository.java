package com.sian.community_api.domain.Comment;

import com.sian.community_api.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface CommentRepository {
    Comment save(Comment comment);
    List<Comment> findByPostId(Long postId);
    void deleteByPostId(Long postId);
    Optional<Comment> findById(Long id);
    void delete(Long id);
}