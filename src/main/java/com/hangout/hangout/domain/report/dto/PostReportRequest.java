package com.hangout.hangout.domain.report.dto;

import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.report.entity.PostReport;
import com.hangout.hangout.domain.report.entity.ReportReason;
import com.hangout.hangout.domain.user.entity.User;
import lombok.Getter;

@Getter
public class PostReportRequest {
    private User user;
    private Post post;
    private ReportReason reportReason;
    private String title;
    private String content;

    public PostReport toEntity() {
        return PostReport.builder()
            .user(user)
            .post(post)
            .reportReason(reportReason)
            .status(null) // todo 추후 처리
            .title(title)
            .content(content)
            .build();
    }
}
