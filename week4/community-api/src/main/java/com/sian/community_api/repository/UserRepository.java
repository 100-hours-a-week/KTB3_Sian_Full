package com.sian.community_api.repository;

import com.sian.community_api.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository extends BaseRepository<User> {

    public UserRepository() {
        if (store.isEmpty()) {
            save(new User(null, "sian@example.com", "sian", "Abcd1234!", false, "profile1.jpeg"));
            save(new User(null, "startup@example.com", "startup", "Asdf0000+", false, "profile2.jpeg"));
            save(new User(null, "spring@example.com", "spring", "Spring1!", false, "profile3.png"));
        }
    }

    // 이메일로 조회
    public Optional<User> findByEmail(String email) {
        return store.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }


    // 닉네임으로 조회
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
