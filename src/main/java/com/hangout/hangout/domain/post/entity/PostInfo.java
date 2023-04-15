package com.hangout.hangout.domain.post.entity;

import com.hangout.hangout.global.common.domain.Map;
import com.hangout.hangout.global.common.domain.Status;
import com.hangout.hangout.domain.user.entity.Gender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Table(name = "POST_INFO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_INFO_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAP_ID")
    private Map map;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID")
    private Status status;
    private String postImage; // 이미지 가져올 url

    @Enumerated(EnumType.STRING)
    @Column(name = "TRAVEL_GENDER", nullable = false)
    private Gender travelGender; // 성별
    @Column(name = "TRAVEL_AGE", nullable = false)
    private String travelAge; // 연령대
    @Column(name = "TRAVEL_AT", nullable = false)
    private String travelAt; // 여행지

    @Column(name = "TRAVEL_MEMBER", nullable = false)
    private int travelMember; // 여행 모집 인원

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date travelDataStart; // 여행 시작 날짜
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date travelDateEnd; // 여행 종료 날짜

}
