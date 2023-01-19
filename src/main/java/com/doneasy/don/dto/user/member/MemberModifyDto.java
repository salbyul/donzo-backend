package com.doneasy.don.dto.user.member;

import lombok.Data;

@Data
public class MemberModifyDto {

    private String memberId;
    private String nickname;
    private String email;
    private String phoneNumber;
    private String password;

}
