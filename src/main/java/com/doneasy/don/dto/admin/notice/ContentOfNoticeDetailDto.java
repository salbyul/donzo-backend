package com.doneasy.don.dto.admin.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContentOfNoticeDetailDto {

    private Integer index;
    private String subtitle;
    private String content;
    private byte[] image;
}
