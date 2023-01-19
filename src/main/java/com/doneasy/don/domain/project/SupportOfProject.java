package com.doneasy.don.domain.project;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SupportOfProject {

    private Long id;
    private LocalDateTime created_date;
    private Long member_id;
    private Long project_id;
}
