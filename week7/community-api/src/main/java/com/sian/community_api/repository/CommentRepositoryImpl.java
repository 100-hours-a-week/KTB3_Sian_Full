package com.sian.community_api.repository;

import com.sian.community_api.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CommentRepositoryImpl extends BaseRepository<Comment> implements CommentRepository {

    @Override
    protected Long getId(Comment comment) {
        return comment.getId();
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        return store.values().stream()
                .filter(comment -> comment.getPostId().equals(postId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByPostId(Long postId) {
        store.entrySet().removeIf(entry -> entry.getValue().getPostId().equals(postId));
    }
}

