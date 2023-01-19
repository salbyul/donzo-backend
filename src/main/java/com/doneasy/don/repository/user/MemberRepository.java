package com.doneasy.don.repository.user;

import com.doneasy.don.domain.user.Member;
import com.doneasy.don.dto.user.member.MemberModifyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberRepository {
    Optional<Member> findByMemberId(String member_id);
    Optional<Member> findByNickname(String nickname);
    Boolean saveMember(Member member);
    Boolean modifyMember(MemberModifyDto memberModifyDto);
}
