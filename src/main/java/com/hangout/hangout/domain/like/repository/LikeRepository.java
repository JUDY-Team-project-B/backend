package com.hangout.hangout.domain.like.repository;

import com.hangout.hangout.domain.like.entity.PostLike;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
    List<PostLike> findAllByUserId(Long userId);
    void deleteByUserAndPost(User user, Post post);
}
