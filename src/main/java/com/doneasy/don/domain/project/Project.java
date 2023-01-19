package com.doneasy.don.domain.project;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Project {

    private Long id;
    private ProjectStatus status;
    private LocalDateTime created_date;
    private Long projectProposal_id;
    private Long organization_id;
}
