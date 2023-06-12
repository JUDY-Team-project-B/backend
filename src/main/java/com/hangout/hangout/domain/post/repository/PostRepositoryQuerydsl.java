package com.hangout.hangout.domain.post.repository;

import com.hangout.hangout.domain.post.entity.Post;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryQuerydsl {

    Optional<Post> findPostById(Long postId);

    Page<Post> findAllPostByCreatedAtDesc(Pageable pageable);

    Page<Post> findAllContainTitleAndContextByCreatedAtDesc(Pageable pageable,
        String searchKeyword);

    Page<Post> findAllContainTitleByCreatedAtDesc(Pageable pageable, String searchKeyword);

    Page<Post> findAllContainContextByCreatedAtDesc(Pageable pageable, String searchKeyword);

    Page<Post> findAllContainNicknameByCreatedAtDesc(Pageable pageable,  String searchKeyword);

    void addLikeCount(Post selectpost);

    void subLikeCount(Post selectpost);

}
