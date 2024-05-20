package com.hangout.hangout.domain.like.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeRequest {
    private Long postId;

    public LikeRequest(Long postId) {
        this.postId = postId;
    }
}
