package com.doneasy.don.repository.project;

import com.doneasy.don.domain.project.SupportOfProject;
import com.doneasy.don.dto.project.supportofproject.SupportOfProjectShowDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SupportOfProjectRepository {

    Integer findCountByMemberId(Long member_id);
    Boolean saveSupport(Long member_id, Long project_id);
    Integer findCountByProjectId(Long id);
    List<SupportOfProjectShowDto> findShowDtoByProjectId(Long projectId);
    List<SupportOfProject> findByProjectId(Long projectId);

    Optional<SupportOfProject> findByMemberIdAndProjectId(Long member_id, Long project_id);

}
