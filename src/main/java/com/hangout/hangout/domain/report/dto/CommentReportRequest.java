package com.hangout.hangout.domain.report.dto;

import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.report.entity.CommentReport;
import com.hangout.hangout.domain.report.entity.ReportReason;
import com.hangout.hangout.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentReportRequest {
    private User user;
    private Comment comment;
    private ReportReason reportReason;
    private String title;
    private String content;

    public CommentReport toEntity() {
        return CommentReport.builder()
            .user(user)
            .comment(comment)
            .reportReason(reportReason)
            .status(null) // todo
            .title(title)
            .content(content)
            .build();
    }
}
