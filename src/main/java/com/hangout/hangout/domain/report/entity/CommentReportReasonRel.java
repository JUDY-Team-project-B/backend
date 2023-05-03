package com.hangout.hangout.domain.report.entity;

import com.hangout.hangout.global.common.domain.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "COMMENT_REPORT_REASON_REL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReportReasonRel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_REPORT_REASON_REL_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_REPORT_ID")
    private CommentReport commentReport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORT_REASON_ID")
    private ReportReason reportReason;
}
