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

    public User createUser(UserSignupRequest request) {

        Optional<User> existingUserOpt = userRepository.findByEmail(request.getEmail());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // 탈퇴 회원 재활성화
            if (existingUser.isDeleted()) {
                if (request.getNickname() != null && !request.getNickname().isBlank()) {
                    existingUser.updateNickname(request.getNickname());
                }

                existingUser.updatePassword(passwordEncoder.encode(request.getPassword()));

                existingUser.restore();

                return existingUser;
            }
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

    public User updateUser(Long userId, String nickname, MultipartFile image) {

        User user = userValidator.findValidUserById(userId);

        if (nickname != null && !nickname.isBlank()) {
            user.updateNickname(nickname);
        }

        if (image != null && !image.isEmpty()) {
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
}
