package com.hangout.hangout.domain.post.dto;


import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.entity.PostInfo;
import com.hangout.hangout.domain.user.entity.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Builder
@RequiredArgsConstructor
public class PostRequest {
    // 클라이언트로부터 포스트 등록을 위해 값을 전달받을 객체 생성
    // 프론트엔드 퍼블리싱이 완전히 끝난 후 다른 점이 있으면 추가 작업할 예정입니다.
    @NotEmpty
    @Length(max = 100, message = "제목은 최대 100자를 넘을 수 없습니다.")
    private final String title;
    @NotEmpty
    private final String context;

    private final String[] tags; // 태그들

    // -- postInfo 정보들
    @NotNull
    private final String travelGender; // 성별
    @NotEmpty
    private final String travelAt; // 여행 지역
    @NotEmpty
    private final String travelAge; // 연령대
    @NotNull
    private final Date travelDateStart; // 여행 시작 날짜
    @NotNull
    private final Date travelDateEnd; // 여행 종료 날짜
    @NotNull
    private final int travelMember; // 여행 모집 인원

    public Gender trueGender(String string) {
        if(string.equals(Gender.MAN.getGender())) return Gender.MAN;
        else return Gender.WOMAN;
    }
}
