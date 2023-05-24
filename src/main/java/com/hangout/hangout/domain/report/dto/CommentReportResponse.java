package com.hangout.hangout.domain.report.dto;

import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.report.entity.CommentReport;
import com.hangout.hangout.domain.report.entity.ReportReason;
import com.hangout.hangout.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentReportResponse {
    private Long id;
    private User user;
    private Comment comment;
    private ReportReason reportReason;
    private String title;
    private String content;

    public static CommentReportResponse of(CommentReport commentReport) {
        return new CommentReportResponse(
            commentReport.getId(),
            commentReport.getUser(),
            commentReport.getComment(),
            commentReport.getReportReason(),
            commentReport.getTitle(),
            commentReport.getContent()
        );
    }
}