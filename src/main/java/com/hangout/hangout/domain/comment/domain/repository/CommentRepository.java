package com.hangout.hangout.domain.comment.domain.repository;

import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    void deleteById(Long id);
    List<Comment> findByPostId(Long id);
    Optional<Comment> findCommentById(Long commentId);

    @Modifying
    @Query("UPDATE Comment c SET c.likeCount = c.likeCount + 1 WHERE c = :comment")
    void addLikeCount(@Param("comment") Comment comment);

    @Modifying
    @Query("UPDATE Comment c SET c.likeCount = c.likeCount - 1 WHERE c = :comment")
    void subLikeCount(@Param("comment") Comment comment);
}