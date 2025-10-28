package com.sian.community_api.repository;

import com.sian.community_api.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {
    private final PasswordEncoder passwordEncoder;

    public UserRepositoryImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

        if (store.isEmpty()) {
            save(User.builder()
                    .email("sian@example.com")
                    .nickname("sian")
                    .password(passwordEncoder.encode("Abcd1234!"))
                    .profileImage("profile1.jpeg")
                    .build());

            save(User.builder()
                    .email("startup@example.com")
                    .nickname("startup")
                    .password(passwordEncoder.encode("Asdf0000!"))
                    .profileImage("profile2.jpeg")
                    .build());

            save(User.builder()
                    .email("spring@example.com")
                    .nickname("spring")
                    .password(passwordEncoder.encode("Spring1!"))
                    .profileImage("profile3.png")
                    .build());
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return store.values().stream()
                .filter(user -> user.getNickname().equals(nickname))
                .findFirst();
    }

    @Override
    protected Long getId(User user) {
        return user.getId();
    }
}
