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
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;
    private final FileStorageService fileStorageService;

    public User createUser(String email, String password, String nickname, MultipartFile image) {

        // 이메일 중복 체크
        Optional<User> existingUserOpt = userRepository.findByEmail(email);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // 탈퇴 회원 재활성화
            if (existingUser.isDeleted()) {
                if (nickname != null && !nickname.isBlank()) {
                    existingUser.updateNickname(nickname);
                }
                existingUser.updatePassword(passwordEncoder.encode(password));
                existingUser.restore();
                return existingUser;
            }
            throw new CustomException(HttpStatus.CONFLICT, "duplicate_email", "이미 사용 중인 이메일입니다.");
        }

        // 중복 닉네임 체크
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new CustomException(HttpStatus.CONFLICT,"duplicate_nickname", "이미 사용 중인 닉네임입니다.");
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .profileImage(null)
                .build();

        // 이미지 업로드
        if (image != null && !image.isEmpty()) {
            String savedPath = fileStorageService.save(image);
            user.updateProfileImage(savedPath);
        } else {
            user.updateProfileImage(null);
        }

        return userRepository.save(user);
    }

    public User updateUser(Long userId, String nickname, MultipartFile image, String profileDeleted) {

        User user = userValidator.findValidUserById(userId);

        if (nickname != null && !nickname.isBlank()) {
            user.updateNickname(nickname);
        }

        // 프로필 삭제
        if ("true".equals(profileDeleted)) {
            deleteOldImageFile(user.getProfileImage());
            user.updateProfileImage(null);
            return user;
        }

        // 프로필 수정
        if (image != null && !image.isEmpty()) {
            deleteOldImageFile(user.getProfileImage());
            String savedPath = fileStorageService.save(image);
            user.updateProfileImage(savedPath);
        }

        return user;
    }




    public void updatePassword(Long userId, String newPassword, String newPasswordConfirm) {

        User user = userValidator.findValidUserById(userId);

        if (user.getPassword().equals(newPassword)) {
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

    private void deleteOldImageFile(String oldImagePath) {
        if (oldImagePath == null || oldImagePath.isBlank()) return;

        fileStorageService.delete(oldImagePath);
    }
}
