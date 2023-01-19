package com.doneasy.don.repository.project;

import com.doneasy.don.domain.project.ContentOfProject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContentOfProjectRepository {

    Boolean saveContent(ContentOfProject contentOfProject);
    String findImageNameByProjectProposalId(Long id);
    List<ContentOfProject> findAllByProjectId(Long project_id);
    List<ContentOfProject> findByProjectProposalId(Long id);

}
