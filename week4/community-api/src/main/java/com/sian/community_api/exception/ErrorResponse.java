package com.sian.community_api.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class ErrorResponse {
    private final int status;
    private final String code; // 식별용 코드 (e.g. duplicate_email)
    private final String message;
    private final LocalDateTime timestamp;
    private Map<String, String> errors; // 필드별 에러 정보
}

