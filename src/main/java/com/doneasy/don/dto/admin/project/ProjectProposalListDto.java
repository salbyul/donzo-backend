package com.doneasy.don.dto.admin.project;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectProposalListDto {

    private Long id;
    private String title;
    private String organizationName;
    private String status;
    private String date;
}
