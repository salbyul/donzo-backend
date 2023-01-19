package com.doneasy.don.domain.project;

import com.doneasy.don.dto.project.projectreview.ProjectReviewSaveDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProjectReview {

    private Long id;
    private String title;
    private LocalDateTime create_date;
    private Long organization_id;
    private Long project_id;


    public static ProjectReview getInstance(ProjectReviewSaveDto projectReviewSaveDto, Long organizationId) {
        return new ProjectReview(null, projectReviewSaveDto.getTitle(), LocalDateTime.now(), organizationId, projectReviewSaveDto.getProjectId());
    }
}
