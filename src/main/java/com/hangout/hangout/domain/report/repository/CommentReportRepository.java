package com.hangout.hangout.domain.report.repository;

import com.hangout.hangout.domain.report.entity.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {

}
