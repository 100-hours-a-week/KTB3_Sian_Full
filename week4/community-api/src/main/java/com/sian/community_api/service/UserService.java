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

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND,
                        "user_not_found",
                        "사용자를 찾을 수 없습니다."
                ));
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

    // 내 정보 수정
    public User updateUser(String email, String newNickname, String newProfileImage) {

        User user = getUserByEmail(email);

        // 닉네임 변경
        if (newNickname != null && !newNickname.isBlank() && !newNickname.equals(user.getNickname())) {
            userRepository.findByNickname(newNickname).ifPresent(existing -> {
                throw new CustomException(HttpStatus.CONFLICT, "duplicate_nickname", "이미 사용 중인 닉네임입니다.");
            });
            user.setNickname(newNickname);
        }

        // 프로필 이미지 변경
        if (newProfileImage != null && !newProfileImage.isBlank() && !newProfileImage.equals(user.getProfileImage())) {
            user.setProfileImage(newProfileImage);
        }

        return user;
    }

    // 회원 탈퇴
    public void deleteUser(String email) {

        User user = getUserByEmail(email);

        if (!user.getEmail().equals(email)) {
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
