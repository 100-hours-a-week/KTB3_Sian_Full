package com.sian.community_api.config;

import com.sian.community_api.domain.User;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public User findValidUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "사용자를 찾을 수 없습니다."));

        if (user.isDeleted()) {
            throw new CustomException(HttpStatus.FORBIDDEN, "DELETED_USER", "탈퇴한 회원의 접근입니다.");
        }

        return user;
    }
}
