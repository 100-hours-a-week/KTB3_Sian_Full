package com.sian.community_api.service;

import com.sian.community_api.exception.CustomException;
import com.sian.community_api.model.User;
import com.sian.community_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public User createUser(User user) {
        // 이메일 중복 409
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new CustomException(HttpStatus.CONFLICT, "duplicate_email", "이미 사용 중인 이메일입니다.");
        }

        // 닉네임 중복 409
        if (userRepository.findByNickname(user.getNickname()).isPresent()) {
            throw new CustomException(HttpStatus.CONFLICT,"duplicate_nickname", "이미 사용 중인 닉네임입니다.");
        }

        return userRepository.save(user);
    }

    // 내 정보 조회

    // 내 정보 수정 (프로필이미지, 닉네임)

    // 회원 탈퇴
    public void deleteUser(Long id, String emailFromToken) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "user_not_found", "존재하지 않는 사용자입니다." ));

        if (!user.getEmail().equals(emailFromToken)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "not_your_account", "본인의 계정이 아닙니다.");
        }

        if (user.isDeleted()) {
            throw new CustomException(HttpStatus.CONFLICT, "already_deleted", "이미 탈퇴한 사용자입니다.");
        }

        user.delete();
    }

    // 사용자 id 오름차순으로 가져오기
    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .sorted(Comparator.comparing(User::getId))
                .toList();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."
                ));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."
                ));
    }
}
