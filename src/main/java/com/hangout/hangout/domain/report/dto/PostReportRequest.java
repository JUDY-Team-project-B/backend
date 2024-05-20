package com.hangout.hangout.domain.report.dto;

import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.report.entity.PostReport;
import com.hangout.hangout.domain.report.entity.ReportReason;
import com.hangout.hangout.domain.report.entity.ReportStatus;
import com.hangout.hangout.domain.user.entity.User;
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
            .reportStatus(ReportStatus.PENDING)
            .title(title)
            .content(content)
            .build();
    }

}
