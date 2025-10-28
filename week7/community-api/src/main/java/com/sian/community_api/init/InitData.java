package com.sian.community_api.init;

import com.sian.community_api.domain.Post;
import com.sian.community_api.domain.User;
import com.sian.community_api.exception.CustomException;
import com.sian.community_api.repository.PostRepository;
import com.sian.community_api.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitData {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        userRepository.save(User.builder()
                .email("sian@example.com")
                .nickname("sian")
                .password(passwordEncoder.encode("Abcd1234!"))
                .profileImage("profile1.jpeg")
                .build());

        userRepository.save(User.builder()
                .email("startup@example.com")
                .nickname("startup")
                .password(passwordEncoder.encode("Asdf0000!"))
                .profileImage("profile2.jpeg")
                .build());

        userRepository.save(User.builder()
                .email("spring@example.com")
                .nickname("spring")
                .password(passwordEncoder.encode("Spring1!"))
                .profileImage("profile3.png")
                .build());

        User sian = userRepository.findById(1L)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "테스트 유저를 찾을 수 없습니다."));

        postRepository.save(Post.builder()
                .author(sian)
                .title("첫 번째 게시글")
                .content("안녕하세요! 커뮤니티 첫 글이에요.")
                .postImage(null)
                .likeCount(5)
                .viewCount(120)
                .commentCount(0)
                .build());

        postRepository.save(Post.builder()
                .author(sian)
                .title("두 번째 게시글")
                .content("오늘은 날씨가 좋아요 ☀️")
                .postImage("image1.png")
                .likeCount(2)
                .viewCount(45)
                .commentCount(1)
                .build());

        postRepository.save(Post.builder()
                .author(sian)
                .title("세 번째 게시글")
                .content("이미지 없는 테스트 게시글입니다.")
                .postImage(null)
                .likeCount(0)
                .viewCount(10)
                .commentCount(0)
                .build());
    }
}
