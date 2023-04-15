package com.hangout.hangout.domain.report.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "POST_REPORT_REASON_REL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReportReasonRel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_REPORT_REASON_REL_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_REPORT_ID")
    private PostReport postReport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORT_REASON_ID")
    private ReportReason reportReason;
}
