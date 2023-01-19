package com.doneasy.don.dto.user.organization;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrganizationSavedto {

    private String organization_id;
    private String password;
    private String email;
    private String nickname;
    private String phone_number;
    private String introduction;
    private String zipcode;
    private String address;
    private String account;
    private String bank;
}
