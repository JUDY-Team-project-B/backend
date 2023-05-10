package com.hangout.hangout.domain.comment.domain.repository;

import com.hangout.hangout.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    void deleteById(Long id);
}
