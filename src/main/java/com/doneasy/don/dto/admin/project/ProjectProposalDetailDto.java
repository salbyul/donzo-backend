package com.doneasy.don.dto.admin.project;

import com.doneasy.don.domain.project.ContentOfProject;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProjectProposalDetailDto {

    private String title;
    private String category;
    private String startDate;
    private String endDate;
    private Integer goal;
    private String createdDate;
    private String deadline;
    private String organizationName;
    private String status;
    private List<ContentOfProject> contentOfProjectList;
}
