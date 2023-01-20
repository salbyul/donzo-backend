package com.doneasy.don.repository.project;

import com.doneasy.don.domain.project.DonationOfProject;
import com.doneasy.don.dto.project.donationofproject.DonationOfProjectShowDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DonationOfProjectRepository {

    Boolean save(DonationOfProject donationOfProject);
    List<DonationOfProject> findByMemberId(Long member_id);
    List<DonationOfProjectShowDto> findAll();
    Integer findSumPriceByProjectId(Long id);
    List<DonationOfProjectShowDto> findAllById(Long id);
    List<DonationOfProject> findByProjectId(Long projectId);

    DonationOfProject findByMemberIdAndProjectId(Long memberId, Long projectId);


}
