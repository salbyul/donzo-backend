package com.doneasy.don.service.user.member;

import com.doneasy.don.domain.project.DonationOfProject;
import com.doneasy.don.domain.user.Member;
import com.doneasy.don.domain.user.Organization;
import com.doneasy.don.dto.user.UserLoginDto;
import com.doneasy.don.dto.user.member.MemberDetailDto;
import com.doneasy.don.dto.user.member.MemberModifyDto;
import com.doneasy.don.exception.user.MemberException;
import com.doneasy.don.repository.project.CommentOfProjectRepository;
import com.doneasy.don.repository.project.DonationOfProjectRepository;
import com.doneasy.don.repository.project.SupportOfProjectRepository;
import com.doneasy.don.repository.user.MemberRepository;
import com.doneasy.don.repository.user.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final CommentOfProjectRepository commentOfProjectRepository;
    private final DonationOfProjectRepository donationOfProjectRepository;
    private final SupportOfProjectRepository supportOfProjectRepository;
    private final MemberRepository memberRepository;
    private final OrganizationRepository organizationRepository;

    public MemberDetailDto getMemberDetail(String memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if (optionalMember.isEmpty()) throw new MemberException(MemberException.INVALID);
        Member findMember = optionalMember.get();
        Long id = findMember.getId();
        log.info("id: {}", id);
        Integer countComment = commentOfProjectRepository.findCountByMemberId(id);
        Integer countSupport = supportOfProjectRepository.findCountByMemberId(id);
        List<DonationOfProject> donationList = donationOfProjectRepository.findByMemberId(id);
        log.info("size: {}", donationList.size());

        int price = 0;
        for (DonationOfProject donationOfProject : donationList) {
            price += donationOfProject.getPrice();
        }

        return new MemberDetailDto(findMember.getNickname(), donationList.size(), price, countSupport, countComment);
    }

    public Boolean login(UserLoginDto userLoginDto) {
        String memberId = userLoginDto.getMemberId();
        String password = userLoginDto.getPassword();
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if (optionalMember.isEmpty()) throw new MemberException(MemberException.INVALID);
        Member findMember = optionalMember.get();
        if (findMember != null && findMember.getPassword().equals(password)) {
            return true;
        }
        return false;
    }


    public void save(Member member) {
        validate(member);
        memberRepository.saveMember(member);
    }

    private void validate(Member member) {
        String memberId = member.getMember_id();

        // 값이 존재하는 지 확인
        if (member.getMember_id() == null || member.getMember_id().equals("") || member.getPassword() == null || member.getPassword().equals("") || member.getNickname() == null || member.getNickname().equals("") ||
                member.getEmail() == null || member.getEmail().equals("") || member.getPhone_number() == null || member.getPhone_number().equals("")) {
            throw new IllegalArgumentException();
        }

        // 아이디가 데이터베이스에 존재하는 지 확인
        Optional<Member> findMember = memberRepository.findByMemberId(memberId);
        Organization findOrganization = organizationRepository.findByOrganizationId(memberId);
        log.info("findMember: {}", findMember);
        log.info("findOrganization: {}", organizationRepository);

        if (findMember.isPresent() || findOrganization != null) {
            throw new MemberException(MemberException.DUPLICATE_ID);
        }
        // 닉네임이 데이터베이스에 존재하는 지 확인
        String nickname = member.getNickname();
        Optional<Member> findMemberByNickname = memberRepository.findByNickname(nickname);
        Organization findOrganizationByNickname = organizationRepository.findByNickname(nickname);

        if (findMemberByNickname.isPresent() || findOrganizationByNickname != null) {
            throw new MemberException(MemberException.DUPLICATE_NICKNAME);
        }
    }

    public void modifyMember(MemberModifyDto memberModifyDto, String memberId) {
        Optional<Member> findMemberByMemberId = memberRepository.findByMemberId(memberId);
        Member member = findMemberByMemberId.get();

        if (!member.getNickname().equals(memberModifyDto.getNickname())) {
            String nickname = memberModifyDto.getNickname();
            Optional<Member> findMemberByNickname = memberRepository.findByNickname(nickname);
            if (findMemberByNickname.isPresent()) throw new MemberException(MemberException.DUPLICATE_NICKNAME);
        }
        if (!memberId.equals(memberModifyDto.getMemberId())) throw new MemberException(MemberException.INVALID);
        memberRepository.modifyMember(memberModifyDto);
    }
}
