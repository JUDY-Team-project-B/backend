package com.hangout.hangout.domain.like.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeRequest {
    private Long userId;
    private Long postId;

    public LikeRequest(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
