package com.hangout.hangout.domain.comment.domain.repository;

import com.hangout.hangout.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> , CommentRepositoryQuerydsl {

}