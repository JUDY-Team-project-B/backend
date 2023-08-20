package com.hangout.hangout.domain.post.dto;

import com.hangout.hangout.domain.user.entity.Gender;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@Builder
@RequiredArgsConstructor
public class PostListResponse {

    private final Long id;
    private final String title;
    private final Long userId;
    private final String nickname;
    private final List<String> tags;
    private final List<String> imageUrls;
    private final String statusType;
    private final Gender travelGender;
    private final String travelAge;
    private final String travelState; // 여행 지역(도)
    private final String travelCity; // 여행 지역(시)
    private final int travelMember;
    private final Date travelDateStart;
    private final Date travelDateEnd;
    private final int likeCount;
    private final Long viewCount;

}
