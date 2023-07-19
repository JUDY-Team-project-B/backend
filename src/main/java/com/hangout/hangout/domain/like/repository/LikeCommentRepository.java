package com.hangout.hangout.domain.like.repository;

import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.like.entity.CommentLike;
import com.hangout.hangout.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
    void deleteByUserAndComment(User user, Comment comment);
}
