package com.sian.community_api.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostPageResponse {

    private List<PostSummaryResponse> content;
    private int totalPages;
    private long totalElements;
    private int size;             // 한 페이지당 최대 게시글 수
    private int number;           // 현재 페이지
    private boolean first;        // 첫 페이지 여부
    private boolean last;         // 마지막 페이지 여부

    public static PostPageResponse from(Page<PostSummaryResponse> page) {
        return PostPageResponse.builder()
                .content(page.getContent())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .number(page.getNumber())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}
