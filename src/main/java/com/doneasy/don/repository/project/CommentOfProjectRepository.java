package com.doneasy.don.repository.project;

import com.doneasy.don.domain.project.CommentOfProject;
import com.doneasy.don.dto.project.commentofproject.CommentOfProjectShowDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentOfProjectRepository {

    Integer findCountByMemberId(Long member_id);

    Integer findByProjectProposalId(Long id);

    List<CommentOfProjectShowDto> findAllById(Long id);

    Boolean saveComment(CommentOfProject commentOfProject);

    List<CommentOfProject> findAll();

    List<CommentOfProject> findByProjectId(Long projectId);

    Boolean statusToBlind(Long id);

    Boolean statusToActive(Long id);

    CommentOfProject findByMemberIdAndProjectId(Long member_id, Long project_id);

}
