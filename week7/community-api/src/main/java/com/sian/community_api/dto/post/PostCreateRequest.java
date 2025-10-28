package com.sian.community_api.dto.post;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequest {

    @Size(max = 26, message = "제목은 최대 26자까지 작성 가능합니다.")
    private String title;

    private String content;
    private String postImage;
}
