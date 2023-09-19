package com.hangout.hangout.domain.like.repository;

import com.hangout.hangout.domain.like.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LikeRepository extends JpaRepository<PostLike, Long>, LikeRepositoryQuerydsl {

}
