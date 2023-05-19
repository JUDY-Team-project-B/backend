package com.hangout.hangout.domain.report.service;

import com.hangout.hangout.domain.report.dto.CommentReportRequest;
import com.hangout.hangout.domain.report.dto.CommentReportResponse;
import com.hangout.hangout.domain.report.dto.PostReportRequest;
import com.hangout.hangout.domain.report.dto.PostReportResponse;
import com.hangout.hangout.domain.report.entity.CommentReport;
import com.hangout.hangout.domain.report.entity.PostReport;
import com.hangout.hangout.domain.report.repository.CommentReportRepository;
import com.hangout.hangout.domain.report.repository.PostReportRepository;
import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final CommentReportRepository commentReportRepository;
    private final PostReportRepository postReportRepository;

    public Long createCommentReport(CommentReportRequest commentReportRequest) {
        CommentReport commentReport = commentReportRequest.toEntity();
        return commentReportRepository.save(commentReport).getId();
    }

    public Long createPostReport(PostReportRequest postReportRequest) {
        PostReport postReport = postReportRequest.toEntity();
        return postReportRepository.save(postReport).getId();
    }

    public CommentReportResponse getCommentReport(Long id) {
        CommentReport commentReport = commentReportRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ResponseType.COMMENT_ABUSE_REPORT_NOT_FOUND));
        return CommentReportResponse.of(commentReport);
    }

    public PostReportResponse getPostReport(Long id) {
        PostReport postReport = postReportRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ResponseType.POST_ABUSE_REPORT_NOT_FOUND));
        return PostReportResponse.of(postReport);
    }

    public List<CommentReportResponse> getCommentReportList() {
        List<CommentReport> commentReports = commentReportRepository.findAll();
        return commentReports.stream()
            .map(CommentReportResponse::of)
            .collect(Collectors.toList());
    }

    public List<PostReportResponse> getPostReportList() {
        List<PostReport> postReports = postReportRepository.findAll();
        return postReports.stream()
            .map(PostReportResponse::of)
            .collect(Collectors.toList());
    }
}
