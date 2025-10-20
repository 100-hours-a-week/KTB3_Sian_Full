package com.sian.community_api.domain.Like;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {
    private Long userId;
    private Long postId;
}
