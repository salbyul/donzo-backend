package com.doneasy.don.dto.user.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDetailDto {
    private String nickname;

    private int donation;

    private int donationPrice;

    private int support;

    private int comment;

}
