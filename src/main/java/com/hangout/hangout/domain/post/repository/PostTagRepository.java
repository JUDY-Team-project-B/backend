package com.hangout.hangout.domain.post.repository;

import com.hangout.hangout.domain.post.entity.PostTagRel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTagRel, Long>, PostTagRepositoryQuerydsl{
    
}
