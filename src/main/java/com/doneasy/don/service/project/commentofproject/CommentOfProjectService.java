package com.doneasy.don.service.project.commentofproject;

import com.doneasy.don.domain.project.CommentOfProject;
import com.doneasy.don.domain.project.Project;
import com.doneasy.don.domain.project.ProjectProposal;
import com.doneasy.don.domain.user.Member;
import com.doneasy.don.dto.project.commentofproject.CommentOfProjectSaveDto;
import com.doneasy.don.exception.project.commentofproject.CommentOfProjectException;
import com.doneasy.don.exception.user.MemberException;
import com.doneasy.don.repository.project.*;
import com.doneasy.don.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentOfProjectService {

    private final CommentOfProjectRepository commentOfProjectRepository;
    private final MemberRepository memberRepository;
    private final SupportOfProjectRepository supportOfProjectRepository;
    private final DonationOfProjectRepository donationOfProjectRepository;
    private final ProjectRepository projectRepository;
    private final ProjectProposalRepository projectProposalRepository;

    public void save(CommentOfProjectSaveDto commentOfProjectSaveDto, String memberId) {
        validatePrice(commentOfProjectSaveDto.getProjectId());

        log.info("haha");
        Optional<Member> optionalFindMember = memberRepository.findByMemberId(memberId);
        if (optionalFindMember.isEmpty()) throw new MemberException(MemberException.INVALID);
        Member member = optionalFindMember.get();
        log.info("good");
        Optional<CommentOfProject> optionalFindCommentOfProject = Optional.ofNullable(commentOfProjectRepository.findByMemberIdAndProjectId(member.getId(), commentOfProjectSaveDto.getProjectId()));
        if (optionalFindCommentOfProject.isPresent()) throw new CommentOfProjectException(CommentOfProjectException.DUPLICATED);
        log.info("HERE");
        commentOfProjectRepository.saveComment(CommentOfProject.getInstance(commentOfProjectSaveDto, member.getId()));

    }

    public Integer getDonationResult(Long projectId) {

        Optional<Integer> support = Optional.ofNullable(supportOfProjectRepository.findCountByProjectId(projectId));
        Optional<Integer> comment = Optional.ofNullable(commentOfProjectRepository.findByProjectProposalId(projectId));
        Optional<Integer> donation = Optional.ofNullable(donationOfProjectRepository.findSumPriceByProjectId(projectId));
        return donation.orElse(0) + (support.orElse(0) * 100) + (comment.orElse(0) * 100);
    }

    // 금액이 초과하는 지 검증
    public void validatePrice(Long projectId) {
        Integer donationResult = getDonationResult(projectId);
        Project findProject = projectRepository.findById(projectId);
        ProjectProposal findProjectProposal = projectProposalRepository.findById(findProject.getProjectProposal_id());
        if (findProjectProposal.getTarget_price() < donationResult + 100)
            throw new CommentOfProjectException(CommentOfProjectException.OVERFLOW_PRICE);
    }
}
