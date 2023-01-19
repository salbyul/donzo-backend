package com.doneasy.don.dto.admin.notice;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeDto {

    private Long id;
    private String subject;
    private LocalDateTime createdDate;
}
