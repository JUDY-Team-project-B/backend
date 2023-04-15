package com.hangout.hangout.domain.report.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "REPORT_REASON")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportReason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_REASON_ID")
    private Long id;

    @Column(name = "REPORT_TYPE")
    private String type;
}
