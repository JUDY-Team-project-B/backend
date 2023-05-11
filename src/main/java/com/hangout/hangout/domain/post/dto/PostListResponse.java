package com.hangout.hangout.domain.post.dto;

import com.hangout.hangout.domain.post.entity.PostTagRel;
import com.hangout.hangout.domain.user.entity.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;


@Getter
@Builder
@RequiredArgsConstructor
public class PostListResponse {
    private final Long id;
    private final String title;
    private final String user;
    private final List<String> tags;

    private final String statusType;
    private final Gender travelGender;
    private final String travelAge;
    private final String travelAt;
    private final int travelMember;
    private final Date travelDateStart;
    private final Date travelDateEnd;


}
