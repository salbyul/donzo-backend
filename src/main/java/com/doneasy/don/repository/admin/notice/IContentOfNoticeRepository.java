package com.doneasy.don.repository.admin.notice;

import com.doneasy.don.domain.admin.notice.ContentsOfNotice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IContentOfNoticeRepository {

    boolean save(ContentsOfNotice contentsOfNotice);
    List<ContentsOfNotice> findByNoticeId(Long id);
}
