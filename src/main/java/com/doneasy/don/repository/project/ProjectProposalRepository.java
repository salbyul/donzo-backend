package com.doneasy.don.repository.project;

import com.doneasy.don.domain.project.ProjectProposal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectProposalRepository {

    ProjectProposal findById(Long id);
    Boolean saveProject(ProjectProposal projectProposal);
    ProjectProposal findRecentByTitle(String title);
    List<ProjectProposal> findByAllTagDonated();
    List<ProjectProposal> findByTagDonated(String tag);
    List<ProjectProposal> findByTag(String tag);
    List<ProjectProposal> findAll();
    List<ProjectProposal> findByTitle(String title);
    List<ProjectProposal> findByTitleOnlySuccess(String title);
    Boolean modifyStatusSuccess(Long id);
    Boolean modifyStatusFail(Long id);

    List<ProjectProposal> findByAllTag();
}
