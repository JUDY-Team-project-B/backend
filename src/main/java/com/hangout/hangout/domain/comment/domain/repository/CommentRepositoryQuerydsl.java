package com.hangout.hangout.domain.comment.domain.repository;

import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryQuerydsl {
    List<Comment> findByPostId(Long postId);

    Optional<Comment> findCommentById(Long commentId);

    List<Comment> findCommentByUser(User user);

    void addLikeCount(Comment selectcomment);

    void subLikeCount(Comment selectcomment);
}
