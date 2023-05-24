package com.hangout.hangout.domain.report.entity;

import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.global.common.domain.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@Table(name = "POST_REPORT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_REPORT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name = "REPORT_REASON")
    private ReportReason reportReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "REPORT_STATUS")
    private ReportStatus reportStatus;

    private String title;
    @Lob
    private String content;

    @Builder
    public PostReport(Long id, User user, Post post, ReportReason reportReason, ReportStatus reportStatus
            , String title, String content) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.reportReason = reportReason;
        this.reportStatus = reportStatus;
        this.title = title;
        this.content = content;
    }
}
