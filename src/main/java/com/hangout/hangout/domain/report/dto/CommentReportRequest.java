package com.hangout.hangout.domain.report.dto;

import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.report.entity.CommentReport;
import com.hangout.hangout.domain.report.entity.ReportReason;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.global.common.domain.entity.Status;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentReportRequest {
    @NotNull
    private Comment comment;
    @NotNull
    private ReportReason reportReason;
    private String title;
    private String content;

    public CommentReport toEntity(User user) {
        return CommentReport.builder()
            .user(user)
            .comment(comment)
            .reportReason(reportReason)
            .status(Status.builder().id(1L).build()) // Set default status with ID 1
            .title(title)
            .content(content)
            .build();
    }
}
