package com.sian.community_api.service;

import com.sian.community_api.config.UserValidator;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.domain.User;
import com.sian.community_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    public User createUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new CustomException(HttpStatus.CONFLICT, "duplicate_email", "이미 사용 중인 이메일입니다.");
        }

        if (userRepository.findByNickname(user.getNickname()).isPresent()) {
            throw new CustomException(HttpStatus.CONFLICT,"duplicate_nickname", "이미 사용 중인 닉네임입니다.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(String email, String newNickname, String newProfileImage) {

        User user = userValidator.findValidUser(email);

        if (newNickname != null && !newNickname.isBlank() && !newNickname.equals(user.getNickname())) {
            userRepository.findByNickname(newNickname).ifPresent(existing -> {
                throw new CustomException(HttpStatus.CONFLICT, "duplicate_nickname", "이미 사용 중인 닉네임입니다.");
            });
            user.setNickname(newNickname);
        }

        if (newProfileImage != null && !newProfileImage.isBlank() && !newProfileImage.equals(user.getProfileImage())) {
            user.setProfileImage(newProfileImage);
        }

        return user;
    }


    public void updatePassword(String email, String currentPassword, String newPassword, String newPasswordConfirm) {

        User user = userValidator.findValidUser(email);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid_current", "현재 비밀번호가 일치하지 않습니다.");
        }

        if (currentPassword.equals(newPassword)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "same_password", "새 비밀번호는 기존 비밀번호와 달라야 합니다.");
        }

        if (!newPassword.equals(newPasswordConfirm)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "password_mismatch", "새 비밀번호와 확인이 일치하지 않습니다.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
    }



    public void deleteUser(String email) {
        User user = userValidator.findValidUser(email);
        user.delete();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."
                ));
    }
}
