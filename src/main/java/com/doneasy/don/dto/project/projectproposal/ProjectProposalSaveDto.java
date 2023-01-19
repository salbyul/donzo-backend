package com.doneasy.don.dto.project.projectproposal;

import com.doneasy.don.dto.project.contentofproject.ContentOfProjectSaveDto;
import lombok.Data;

import java.util.List;

@Data
public class ProjectProposalSaveDto {

    private String service_start_date;
    private String service_end_date;
    private String deadline;
    private String category;
    private Integer target_price;
    private String title;
    private List<ContentOfProjectSaveDto> contentOfProjectSaveDtoList;
}
