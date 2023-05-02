package com.hangout.hangout.Post.domain.repository;

import com.hangout.hangout.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.id = :postId and p.postInfo.status.id = 1")
    Optional<Post> findPostById(Long postId);
}
