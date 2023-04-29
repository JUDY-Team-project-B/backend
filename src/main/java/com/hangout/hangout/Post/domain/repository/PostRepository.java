package com.hangout.hangout.Post.domain.repository;

import com.hangout.hangout.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
