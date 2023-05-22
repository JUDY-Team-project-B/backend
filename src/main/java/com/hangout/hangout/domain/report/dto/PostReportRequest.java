package com.hangout.hangout.domain.report.dto;

import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.report.entity.PostReport;
import com.hangout.hangout.domain.report.entity.ReportReason;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.global.common.domain.entity.Status;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostReportRequest {
    @NotNull
    private Post post;
    @NotNull
    private ReportReason reportReason;
    private String title;
    private String content;

    public PostReport toEntity(User user) {
        return PostReport.builder()
            .user(user)
            .post(post)
            .reportReason(reportReason)
            .status(Status.builder().id(1L).build()) // Set default status with ID 1
            .title(title)
            .content(content)
            .build();
    }
}
