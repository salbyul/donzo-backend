package com.doneasy.don.dto.user.organization;

import com.doneasy.don.domain.user.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizationGetModifyDto {

    private String organization_id;
    private String nickname;
    private String email;
    private String phone_number;
    private String zipcode;
    private String address;
    private String bank;
    private String account;
    private String introduction;
    private byte[] image;

    public static OrganizationGetModifyDto getInstance(Organization organization, byte[] image) {
        return new OrganizationGetModifyDto(organization.getOrganization_id(), organization.getNickname(), organization.getEmail(), organization.getPhone_number(), organization.getZipcode(), organization.getAddress(), organization.getBank(), organization.getAccount(), organization.getIntroduction(), image);
    }
}
