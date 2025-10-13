package com.sian.community_api.repository;

import com.sian.community_api.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CommentRepository extends BaseRepository<Comment> {

    @Override
    protected Long getId(Comment comment) {
        return comment.getId();
    }

    @Override
    protected void setId(Comment comment, Long id) {
        comment.setId(id);
    }

    public List<Comment> findByPostId(Long postId) {
        return store.values().stream()
                .filter(comment -> comment.getPostId().equals(postId))
                .collect(Collectors.toList());
    }

    public void deleteByPostId(Long postId) {
        store.entrySet().removeIf(entry -> entry.getValue().getPostId().equals(postId));
    }
}
