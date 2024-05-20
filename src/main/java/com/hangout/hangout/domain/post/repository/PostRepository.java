package com.hangout.hangout.domain.post.repository;

import com.hangout.hangout.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryQuerydsl {

}
