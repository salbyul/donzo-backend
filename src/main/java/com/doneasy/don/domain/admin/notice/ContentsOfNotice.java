package com.doneasy.don.domain.admin.notice;

import com.doneasy.don.dto.admin.notice.ContentOfNoticeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ContentsOfNotice {

    private Long id;
    private String subtitle;
    private String content;
    private String image_name;
    private Integer order_num;
    private LocalDateTime created_date;
    private LocalDateTime modified_date;
    private Long notice_id;

    public static ContentsOfNotice getContentsOfNotice(ContentOfNoticeDto contentOfNoticeDto, int index, String imageName, Long noticeId) {
        return new ContentsOfNotice(null, contentOfNoticeDto.getSubtitle(), contentOfNoticeDto.getContent(), imageName, index, LocalDateTime.now(), LocalDateTime.now(), noticeId);
    }
}
