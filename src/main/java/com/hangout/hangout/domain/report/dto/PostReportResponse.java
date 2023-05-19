package com.hangout.hangout.domain.report.dto;

import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.report.entity.PostReport;
import com.hangout.hangout.domain.report.entity.ReportReason;
import com.hangout.hangout.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostReportResponse {
    private Long id;
    private User user;
    private Post post;
    private ReportReason reportReason;
    private String title;
    private String content;

    public static PostReportResponse of(PostReport postReport) {
        return new PostReportResponse(
            postReport.getId(),
            postReport.getUser(),
            postReport.getPost(),
            postReport.getReportReason(),
            postReport.getTitle(),
            postReport.getContent()
        );
    }
}