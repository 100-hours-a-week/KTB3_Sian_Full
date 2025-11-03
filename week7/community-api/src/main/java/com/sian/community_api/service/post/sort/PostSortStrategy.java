package com.sian.community_api.service.post.sort;

import com.sian.community_api.entity.Post;

import java.util.Comparator;

public interface PostSortStrategy {
    Comparator<Post> getComparator();
}
