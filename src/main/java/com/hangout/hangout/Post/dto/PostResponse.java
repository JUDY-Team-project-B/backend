package com.hangout.hangout.Post.dto;

import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.entity.PostInfo;
import com.hangout.hangout.domain.post.entity.PostTagRel;
import com.hangout.hangout.domain.user.entity.Gender;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.global.common.domain.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Builder
@RequiredArgsConstructor
public class PostResponse {
    // 게시글 특정 게시물 조회 시 반환할 목록 클래스
    // 추후 기능 만들고 추가해야 할 것 : Map, 좋아요, 신고, 조회수, 이미지
    private final Long id;
    private final String title;
    private final String user;
    private final String context;
    private final Set<PostTagRel> tags;
    private final String statusType;
    private final Gender travelGender;
    private final String travelAge;
    private final String travelAt;
    private final int travelMember;
    private final Date travelDateStart;
    private final Date travelDateEnd;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public static PostResponse of(Post post) { // 유저 정보 추가 예정
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .context(post.getContext())
                .tags(post.getTags())
                .statusType(post.getPostInfo().getStatus().getType())
                .travelGender(post.getPostInfo().getTravelGender())
                .travelAge(post.getPostInfo().getTravelAge())
                .travelAt(post.getPostInfo().getTravelAt())
                .travelMember(post.getPostInfo().getTravelMember())
                .travelDateStart(post.getPostInfo().getTravelDateStart())
                .travelDateEnd(post.getPostInfo().getTravelDateEnd())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
