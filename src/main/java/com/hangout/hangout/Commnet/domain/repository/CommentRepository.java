package com.hangout.hangout.Commnet.domain.repository;

import com.hangout.hangout.Commnet.dto.CommentSaveRequestDto;
import com.hangout.hangout.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    void deleteById(Long id);
}
