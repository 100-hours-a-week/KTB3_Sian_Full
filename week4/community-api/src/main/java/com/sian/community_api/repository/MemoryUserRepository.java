package com.sian.community_api.repository;

import com.sian.community_api.domain.User.User;
import com.sian.community_api.domain.User.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemoryUserRepository extends BaseRepository<User> implements UserRepository {
    private final PasswordEncoder passwordEncoder;

    public MemoryUserRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

        if (store.isEmpty()) {
            save(new User(null, "sian@example.com", "sian", passwordEncoder.encode("Abcd1234!"), false, "profile1.jpeg"));
            save(new User(null, "startup@example.com", "startup", passwordEncoder.encode("Asdf0000!"), false, "profile2.jpeg"));
            save(new User(null, "spring@example.com", "spring", passwordEncoder.encode("Spring1!"), false, "profile3.png"));
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

    @Override
    protected  void setId(User user, Long id) {
        user.setId(id);
    }
}
