package com.doneasy.don.repository.project;

import com.doneasy.don.domain.project.Project;
import com.doneasy.don.dto.project.project.ProjectShowDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectRepository {

    Boolean save(Long projectProposalId, Long organizationId);

    Project findById(Long id);
    List<Project> findAll();

    Project findByProjectProposalId(Long projectProposalId);
    Boolean statusToDone(Long id);
    List<Project> findByOrganizationId(Long organization_id);
    ProjectShowDto findProjectInfoByProjectId(Long id);
    // TODO 프로젝트 리뷰를 찾는 데 왜 프로젝트 레포지토리에?
    ProjectShowDto findProjectReviewInfoByProjectId(Long id);

}
