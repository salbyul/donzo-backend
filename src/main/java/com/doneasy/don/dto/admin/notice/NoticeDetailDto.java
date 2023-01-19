package com.doneasy.don.dto.admin.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class NoticeDetailDto {

    private String title;
    private List<ContentOfNoticeDetailDto> contentOfNoticeDetailDtoList;


}
