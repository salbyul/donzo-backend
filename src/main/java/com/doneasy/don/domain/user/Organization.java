package com.doneasy.don.domain.user;

import com.doneasy.don.dto.user.organization.OrganizationSavedto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Organization {

    private Long id;
    private String organization_id;
    private String password;
    private String email;
    private String nickname;
    private String phone_number;
    private String image_name;
    private String introduction;
    private String zipcode;
    private String address;
    private String account;
    private String bank;
    private LocalDateTime created_date;
    private LocalDateTime modified_date;

    public static Organization getOrganization(OrganizationSavedto organizationSavedto, String image_name) {
        return new Organization(null, organizationSavedto.getOrganization_id(), organizationSavedto.getPassword(),
                organizationSavedto.getEmail(), organizationSavedto.getNickname(), organizationSavedto.getPhone_number(), image_name,
                organizationSavedto.getIntroduction(), organizationSavedto.getZipcode(), organizationSavedto.getAddress(),
                organizationSavedto.getAccount(), organizationSavedto.getBank(), LocalDateTime.now(), LocalDateTime.now());
    }
}
