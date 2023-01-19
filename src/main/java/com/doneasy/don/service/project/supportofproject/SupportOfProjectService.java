package com.doneasy.don.service.project.supportofproject;

import com.doneasy.don.domain.project.SupportOfProject;
import com.doneasy.don.domain.user.Member;
import com.doneasy.don.exception.project.supportofproject.SupportOfProjectException;
import com.doneasy.don.exception.user.MemberException;
import com.doneasy.don.repository.project.SupportOfProjectRepository;
import com.doneasy.don.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupportOfProjectService {

    private final SupportOfProjectRepository supportOfProjectRepository;
    private final MemberRepository memberRepository;


    public void save(Long projectId, String memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if (optionalMember.isEmpty()) throw new MemberException(MemberException.INVALID);
        Member member = optionalMember.get();
        Optional<SupportOfProject> optionalSupportOfProject = supportOfProjectRepository.findByMemberIdAndProjectId(member.getId(), projectId);
        if (optionalSupportOfProject.isPresent()) throw new SupportOfProjectException(SupportOfProjectException.DUPLICATED);
        supportOfProjectRepository.saveSupport(member.getId(), projectId);
    }
}
