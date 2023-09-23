package com.hangout.hangout.domain.like.repository;

import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.like.entity.CommentLike;
import com.hangout.hangout.domain.user.entity.User;

import java.util.Optional;

public interface LikeCommentRepositoryQuerydsl {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);

    void deleteCommentLikeByUserAndComment(User user, Comment comment);
}
