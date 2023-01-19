package com.doneasy.don.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Member {

    private Long id;
    private String member_id;
    private String password;
    private String email;
    private String nickname;
    private String phone_number;
    private MemberStatus status;
    private Grade grade;
    private LocalDateTime created_date;
    private LocalDateTime modified_date;

    public static Member getMemberExceptPassword(Member member) {
        return new Member(member.getId(), member.getMember_id(), null, member.getEmail(), member.getNickname(), member.getPhone_number(), member.getStatus(), member.getGrade(), member.getCreated_date(), member.getModified_date());
    }
}
