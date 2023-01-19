package com.doneasy.don.dto.admin.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class NoticeSaveDto {

    private String title;
    private List<ContentOfNoticeDto> contextList;

}
