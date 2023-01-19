package com.doneasy.don.domain.admin.notice;

import com.doneasy.don.dto.admin.notice.NoticeSaveDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Notice implements Comparable<Notice>{

    private Long id;
    private String title;
    private LocalDateTime created_date;
    private LocalDateTime modified_date;

    public static Notice getNotice(NoticeSaveDto noticeSaveDto) {
        return new Notice(null, noticeSaveDto.getTitle(), LocalDateTime.now(), LocalDateTime.now());
    }

    @Override
    public int compareTo(Notice o) {
        return this.compareTo(o);
    }
}
