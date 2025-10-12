package com.sian.community_api.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Like {
    private Long id;
    private Long userId;
    private Long postId;
}
