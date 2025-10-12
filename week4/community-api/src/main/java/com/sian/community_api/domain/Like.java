package com.sian.community_api.domain;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {
    private Long id;
    private Long userId;
    private Long postId;
}
