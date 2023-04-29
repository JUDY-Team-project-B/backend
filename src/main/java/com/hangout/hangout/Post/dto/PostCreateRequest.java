package com.hangout.hangout.Post.dto;


import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.entity.PostInfo;
import com.hangout.hangout.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@NoArgsConstructor
public class PostCreateRequest {
    // 클라이언트로부터 포스트 등록을 위해 값을 전달받을 객체 생성
    // 프론트엔드 퍼블리싱이 완전히 끝난 후 다른 점이 있으면 추가 작업할 예정입니다.
    @NotEmpty
    @Length(max = 100, message = "제목은 최대 100자를 넘을 수 없습니다.")
    private String title;
    @NotEmpty
    private String context;
    // -- postInfo 정보들
    @NotEmpty
    private String travelAt; // 여행 지역
    @NotEmpty
    private Date travelDataStart; // 여행 시작 날짜
    @NotEmpty
    private Date travelDateEnd; // 여행 종료 날짜
    @NotEmpty
    private int travelMember; // 여행 모집 인원

    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .title(this.title)
                .context(this.context)
                .postInfo(PostInfo.builder()
                        .travelAt(travelAt)
                        .travelDataStart(travelDataStart)
                        .travelDateEnd(travelDateEnd)
                        .travelMember(travelMember)
                        .build())
                .build();
    }
}
