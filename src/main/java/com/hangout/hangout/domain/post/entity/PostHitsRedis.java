package com.hangout.hangout.domain.post.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Redis에 저장되는 객체로, 현재 Id에 들어가는 정보는 postId + "-" + userId로 임시 설정
 * timeToLive 값은 추후 논의 후 결정하기
 */
@Getter
@Builder
@RedisHash(value = "postHits", timeToLive = 24 * 60 * 60)
public class PostHitsRedis {

    @Id
    private String id;

}

