package com.doneasy.don.dto.project.projectreview;

import com.doneasy.don.domain.project.ContentOfProjectReview;
import com.doneasy.don.domain.project.ContentOfProjectStatus;
import com.doneasy.don.dto.project.contentofprojectreview.ContentOfProjectReviewSaveDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectReviewSaveDto {

    private String title;
    private Long projectId;
    private List<ContentOfProjectReviewSaveDto> contentOfProjectReviewSaveDtoList;

    public static ContentOfProjectReview getContentOfProjectReview(ContentOfProjectReviewSaveDto contentOfProjectReviewSaveDto, int index, String image_name, Long projectReview_id) {
        return new ContentOfProjectReview(null, contentOfProjectReviewSaveDto.getSubtitle(), contentOfProjectReviewSaveDto.getContents(), image_name, index, LocalDateTime.now().toString(), projectReview_id);
    }
}
