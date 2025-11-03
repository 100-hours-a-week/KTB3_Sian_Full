package com.sian.community_api.service.post.sort;

import com.sian.community_api.entity.Post;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component("likes")
public class SortByLikes implements PostSortStrategy{
    @Override
    public Comparator<Post> getComparator() {
        return Comparator.comparing(Post::getLikeCount);
    }
}
