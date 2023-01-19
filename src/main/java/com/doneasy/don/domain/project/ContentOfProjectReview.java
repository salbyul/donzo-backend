package com.doneasy.don.domain.project;

import com.doneasy.don.dto.project.contentofprojectreview.ContentOfProjectReviewSaveDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ContentOfProjectReview {

    private Long id;
    private String subtitle;
    private String contents;
    private String image_name;
    private Integer order_num;
    private String create_date;
    private Long projectReview_id;


    public static ContentOfProjectReview getContentOfProjectReview(ContentOfProjectReviewSaveDto contentOfProjectReviewSaveDto, int index, String image_name, Long projectReview_id) {
        return new ContentOfProjectReview(null, contentOfProjectReviewSaveDto.getSubtitle(), contentOfProjectReviewSaveDto.getContents(), image_name, index, null, projectReview_id);
    }
}
