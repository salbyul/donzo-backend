package com.doneasy.don.domain.project;

import com.doneasy.don.dto.project.usageplan.UsagePlanSaveDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsagePlan {

    private Long id;
    private String name;
    private Integer price;
    private Long projectproposal_id;

    public static UsagePlan usagePlan(UsagePlanSaveDto usagePlanSaveDto, Long projectProposalId) {
        return new UsagePlan(null, usagePlanSaveDto.getName(), usagePlanSaveDto.getPrice(), projectProposalId);
    }
}
