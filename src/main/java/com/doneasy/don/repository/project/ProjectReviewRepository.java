package com.doneasy.don.repository.project;

import com.doneasy.don.domain.project.ProjectReview;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectReviewRepository {
    Boolean saveReview(ProjectReview projectProposalReview);
    ProjectReview findReviewRecentByTitle(String title);
    ProjectReview findProjectReviewByProjectId(Long id);

    List<ProjectReview> findByTitle(String title);
}
