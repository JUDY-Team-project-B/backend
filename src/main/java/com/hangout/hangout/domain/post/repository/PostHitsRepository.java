package com.hangout.hangout.domain.post.repository;

import com.hangout.hangout.domain.post.entity.PostHits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHitsRepository extends JpaRepository<PostHits, Long>, PostHitsRepositoryQuerydsl {

}
