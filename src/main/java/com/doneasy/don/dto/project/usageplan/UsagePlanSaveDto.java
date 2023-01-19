package com.doneasy.don.dto.project.usageplan;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsagePlanSaveDto {

    private String name;
    private Integer price;
    private Long projectproposal_id;
}
