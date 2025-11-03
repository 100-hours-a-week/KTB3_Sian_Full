package com.sian.community_api.service;

import com.sian.community_api.config.UserValidator;
import com.sian.community_api.dto.user.UserSignupRequest;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.entity.User;
import com.sian.community_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    public User createUser(UserSignupRequest request) {
        if (!request.isPasswordConfirmed()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "password_mismatch", "비밀번호가 일치하지 않습니다.");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(HttpStatus.CONFLICT, "duplicate_email", "이미 사용 중인 이메일입니다.");
        }

        if (userRepository.findByNickname(request.getNickname()).isPresent()) {
            throw new CustomException(HttpStatus.CONFLICT,"duplicate_nickname", "이미 사용 중인 닉네임입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .profileImage(request.getProfileImage())
                .build();

        return userRepository.save(user);
    }

    public User updateUser(Long userId, String newNickname, String newProfileImage) {

        User user = userValidator.findValidUserById(userId);

        if (newNickname != null && !newNickname.isBlank() && !newNickname.equals(user.getNickname())) {
            userRepository.findByNickname(newNickname).ifPresent(existing -> {
                throw new CustomException(HttpStatus.CONFLICT, "duplicate_nickname", "이미 사용 중인 닉네임입니다.");
            });
            user.updateNickname(newNickname);
        }

        if (newProfileImage != null && !newProfileImage.isBlank() && !newProfileImage.equals(user.getProfileImage())) {
            user.updateProfileImage(newProfileImage);
        }

        return user;
    }


    public void updatePassword(Long userId, String currentPassword, String newPassword, String newPasswordConfirm) {

        User user = userValidator.findValidUserById(userId);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid_current", "현재 비밀번호가 일치하지 않습니다.");
        }

        if (currentPassword.equals(newPassword)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "same_password", "새 비밀번호는 기존 비밀번호와 달라야 합니다.");
        }

        if (!newPassword.equals(newPasswordConfirm)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "password_mismatch", "새 비밀번호와 확인이 일치하지 않습니다.");
        }

        user.updatePassword(passwordEncoder.encode(newPassword));
    }

    public void deleteUser(Long userId) {
        User user = userValidator.findValidUserById(userId);
        user.delete();
    }
}
