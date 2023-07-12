package com.hangout.hangout.domain.post.repository;

import com.hangout.hangout.domain.post.entity.PostHitsRedis;
import org.springframework.data.repository.CrudRepository;

public interface PostHitsRedisRepository extends CrudRepository<PostHitsRedis, String> {

}
