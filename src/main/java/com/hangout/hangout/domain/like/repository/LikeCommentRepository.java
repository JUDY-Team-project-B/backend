package com.hangout.hangout.domain.like.repository;

import com.hangout.hangout.domain.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LikeCommentRepository extends JpaRepository<CommentLike, Long>,
    LikeCommentRepositoryQuerydsl {

}
