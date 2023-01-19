package com.doneasy.don.dto.admin.project;

import com.doneasy.don.domain.project.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectListDto {

    private Long id;
    private String title;
    private String organizationName;
    private String account;
    private Integer donationPrice;
    private Integer supportPrice;
    private ProjectStatus status;
    private String deadline;
}
