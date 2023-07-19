package com.hangout.hangout.domain.post.repository;

import com.hangout.hangout.global.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

@Import(TestConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PostRepositoryQuerydslImplTest {

    @Autowired
    private PostRepository postRepository;

    // 게시물을 조회 수가 많은 순으로 조회
    @Test
    void findAllByOrderByPostHits() {
        PageRequest page = PageRequest.of(0, 8);
        boolean isDescending = true;

        postRepository.findAllByOrderByPostHits(page,
            isDescending).getContent();
    }
}

