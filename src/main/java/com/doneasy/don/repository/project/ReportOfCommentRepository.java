package com.doneasy.don.repository.project;

import com.doneasy.don.domain.project.ReportOfComment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportOfCommentRepository {

    Boolean saveReportOfComment(ReportOfComment reportOfComment);
    Long findByCommentOfProjectId(Long id);

    ReportOfComment findByCommentIdAndMemberId(Long memberId, Long commentId);
}
