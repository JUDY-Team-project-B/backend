package com.hangout.hangout.domain.post.entity;

import com.hangout.hangout.domain.report.entity.CommentReport;
import com.hangout.hangout.domain.report.entity.PostReport;
import com.hangout.hangout.global.common.domain.BaseEntity;
import com.hangout.hangout.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "POST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    // 좋아요 기능 추가를 위해 PostLike 와 연결
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<PostLike> postLikes = new ArrayList<>();

    // 신고 기능 추가를 위해 PostReport 와 연결
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<PostReport> postReports = new ArrayList<>();

    @Lob
    private String context;

}
