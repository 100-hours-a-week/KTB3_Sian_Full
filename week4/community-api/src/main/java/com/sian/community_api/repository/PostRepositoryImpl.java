package com.sian.community_api.repository;

import com.sian.community_api.domain.Post.Post;
import com.sian.community_api.domain.Post.PostRepository;
import com.sian.community_api.domain.User.User;
import com.sian.community_api.domain.User.UserRepository;
import com.sian.community_api.exception.CustomException;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PostRepositoryImpl extends BaseRepository<Post> implements PostRepository {

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        User sian = userRepository.findById(1L)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "테스트 유저를 찾을 수 없습니다."));

        save(Post.builder()
                .author(sian)
                .title("첫 번째 게시글")
                .content("안녕하세요! 커뮤니티 첫 글이에요.")
                .postImage(null)
                .likeCount(5)
                .viewCount(120)
                .commentCount(0)
                .build());

        save(Post.builder()
                .author(sian)
                .title("두 번째 게시글")
                .content("오늘은 날씨가 좋아요 ☀️")
                .postImage("image1.png")
                .likeCount(2)
                .viewCount(45)
                .commentCount(1)
                .build());

        save(Post.builder()
                .author(sian)
                .title("세 번째 게시글")
                .content("이미지 없는 테스트 게시글입니다.")
                .postImage(null)
                .likeCount(0)
                .viewCount(10)
                .commentCount(0)
                .build());
    }

    @Override
    protected Long getId(Post model) {
        return model.getId();
    }

    @Override
    protected void setId(Post model, Long id) {
        model.setId(id);
    }
}

