package com.doneasy.don.dto.user.member;

import lombok.Data;

@Data
public class MemberSaveDto {

    private String member_id;
    private String password;
    private String email;
    private String nickname;
    private String phone_number;
}
