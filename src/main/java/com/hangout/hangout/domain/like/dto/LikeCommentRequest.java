package com.hangout.hangout.domain.like.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeCommentRequest {
    private Long userId;
    private Long commentId;

    public LikeCommentRequest(Long userId, Long commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }
}
