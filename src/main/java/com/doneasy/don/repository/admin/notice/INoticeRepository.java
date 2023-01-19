package com.doneasy.don.repository.admin.notice;


import com.doneasy.don.domain.admin.notice.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface INoticeRepository {

    List<Notice> findAll();
    Boolean save(Notice notice);
    Notice findRecent();
    Notice findById(Long id);
    Boolean deleteById(Long id);
    List<Notice> findByTitle(String title);
}
