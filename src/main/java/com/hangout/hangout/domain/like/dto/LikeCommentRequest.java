package com.hangout.hangout.domain.like.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeCommentRequest {
    private Long commentId;

    public LikeCommentRequest(Long commentId) {
        this.commentId = commentId;
    }
}
