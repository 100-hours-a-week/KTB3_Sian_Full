package com.sian.community_api.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class ErrorResponse {
    private final int status; // status code
    private final String code; // 식별용 코드 (e.g. duplicate_email)
    private final String message; // 사용자에게 보여지는 메시지
    private final LocalDateTime timestamp;
    private Map<String, String> errors; // 유효성 검사 실패 시 필드별 에러 정보
}

