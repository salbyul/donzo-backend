package com.doneasy.don.dto.user;

import lombok.Data;

@Data
public class UserLoginDto {

    private String memberId;
    private String password;
    private String target;

}
