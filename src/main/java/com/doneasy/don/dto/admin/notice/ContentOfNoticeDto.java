package com.doneasy.don.dto.admin.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContentOfNoticeDto {

    private Long index;
    private String subtitle;
    private String content;
}
