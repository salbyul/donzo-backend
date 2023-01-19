package com.doneasy.don.repository.project;

import com.doneasy.don.domain.project.ContentOfProject;
import com.doneasy.don.domain.project.ContentOfProjectReview;
import com.doneasy.don.dto.project.contentofprojectreview.ContentOfProjectReviewSaveDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContentOfProjectReviewRepository {

    Boolean saveContentOfProjectReview(ContentOfProjectReview contentOfProjectReview);
    String findImageNameByProjectReviewId(Long id);
    List<ContentOfProjectReview> findContentForProjectReview(Long projectId);


}
