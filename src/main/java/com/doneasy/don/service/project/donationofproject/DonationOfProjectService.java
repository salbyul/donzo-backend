package com.doneasy.don.service.project.donationofproject;

import com.doneasy.don.domain.project.DonationOfProject;
import com.doneasy.don.domain.project.Project;
import com.doneasy.don.domain.project.ProjectProposal;
import com.doneasy.don.domain.user.Member;
import com.doneasy.don.exception.project.donationofproject.DonationOfProjectException;
import com.doneasy.don.exception.user.MemberException;
import com.doneasy.don.repository.project.*;
import com.doneasy.don.repository.user.MemberRepository;
import com.doneasy.don.service.project.donationofproject.kakao.KakaoSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonationOfProjectService {

    private final DonationOfProjectRepository donationOfProjectRepository;
    private final MemberRepository memberRepository;
    private final KakaoSession kakaoSession;
    private final SupportOfProjectRepository supportOfProjectRepository;
    private final CommentOfProjectRepository commentOfProjectRepository;
    private final ProjectRepository projectRepository;
    private final ProjectProposalRepository projectProposalRepository;

    public void save(String memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if (optionalMember.isEmpty()) throw new MemberException(MemberException.INVALID);
        donationOfProjectRepository.save(new DonationOfProject(null, kakaoSession.getValue(memberId), null, optionalMember.get().getId(), kakaoSession.getProjectId(memberId)));
    }

    public void validate(String memberId, Long projectId, Integer value) {
        Integer donationResult = getDonationResult(projectId);
        Project findProject = projectRepository.findById(projectId);
        ProjectProposal findProjectProposal = projectProposalRepository.findById(findProject.getProjectProposal_id());
        if (findProjectProposal.getTarget_price() < donationResult + value) {
            throw new DonationOfProjectException(DonationOfProjectException.OVERFLOW_PRICE);
        }

        Optional<Member> findMember = memberRepository.findByMemberId(memberId);
        if (findMember.isEmpty()) throw new MemberException(MemberException.INVALID);
        Member member = findMember.get();
        DonationOfProject findDonationOfProject = donationOfProjectRepository.findByMemberIdAndProjectId(member.getId(), projectId);
        if (findDonationOfProject != null) throw new MemberException(MemberException.DUPLICATE_ID);
    }

    public Integer getDonationResult(Long projectId) {

        Optional<Integer> support = Optional.ofNullable(supportOfProjectRepository.findCountByProjectId(projectId));
        Optional<Integer> comment = Optional.ofNullable(commentOfProjectRepository.findByProjectProposalId(projectId));
        Optional<Integer> donation = Optional.ofNullable(donationOfProjectRepository.findSumPriceByProjectId(projectId));
        return donation.orElse(0) + (support.orElse(0) * 100) + (comment.orElse(0) * 100);
    }
}
