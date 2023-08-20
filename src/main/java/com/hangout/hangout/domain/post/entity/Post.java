package com.hangout.hangout.domain.post.entity;

import com.hangout.hangout.domain.like.entity.PostLike;
import com.hangout.hangout.domain.post.dto.PostRequest;
import com.hangout.hangout.domain.report.entity.PostReport;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.global.common.domain.entity.BaseEntity;

import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Table(name = "POST")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "POST_INFO_ID", nullable = false, referencedColumnName = "POST_INFO_ID")
    private PostInfo postInfo;

    @ColumnDefault("0")
    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    // 신고 기능 추가를 위해 PostReport 와 연결
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<PostReport> postReports = new ArrayList<>();

    // ManyToMany 관계를 위해서 일대다로 관계맺어준 컬럼
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTagRel> postTagRels;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostLike> postLikes = new HashSet<>();

    @OneToMany(mappedBy = "post")
    private List<PostHits> postHits = new ArrayList<>();

    @Lob
    private String context;

    @Builder

    public Post(String title, User user, PostInfo postInfo, List<PostReport> postReports,
                Integer likeCount ,List<PostTagRel> postTagRels , String context) {
        this.title = title;
        this.user = user;
        this.postInfo = postInfo;
        this.likeCount = likeCount;
        this.postReports = postReports;
        this.postTagRels = postTagRels;
        this.context = context;
    }

    public void updatePost(PostRequest postRequest) {
        this.title = postRequest.getTitle();
        this.context = postRequest.getContext();
    }
}
