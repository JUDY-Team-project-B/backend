package com.hangout.hangout.Post.dto;


import com.hangout.hangout.global.common.domain.Map;
import com.hangout.hangout.global.common.domain.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public class PostCreateRequest {
    // 클라이언트로부터 포스트 등록을 위해 값을 전달받을 객체 생성
    // 이미지는 추후 작업 추가할 예정입니다.

    @NotEmpty
    @Length(max = 100, message = "제목은 최대 100자를 넘을 수 없습니다.")
    private final String title;

    @NotEmpty
    private final String content;
    @NotEmpty
    private final Map map; // 여행 지역 // 추후 Map 데이터베이스에 따라서 변경될 수 있음
    @NotEmpty
    private final Date travelDataStart; // 여행 시작 날짜
    @NotEmpty
    private final Date travelDateEnd; // 여행 종료 날짜
    @NotEmpty
    private final int travelMember; // 여행 모집 인원
    @NotEmpty
    private final Tag tag; // 일단 하나로 작성 // 두 개 이상의 태그를 작성할 수 있을 경우 List로 변경할 예정

}
