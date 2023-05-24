package com.hangout.hangout.domain.report.entity;

import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.global.common.domain.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "COMMENT_REPORT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_REPORT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

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
    public CommentReport(Long id, User user, Comment comment, ReportReason reportReason, ReportStatus reportStatus
    , String title, String content) {
        this.id = id;
        this.user = user;
        this.comment = comment;
        this.reportReason = reportReason;
        this.reportStatus = reportStatus;
        this.title = title;
        this.content = content;
    }

}
