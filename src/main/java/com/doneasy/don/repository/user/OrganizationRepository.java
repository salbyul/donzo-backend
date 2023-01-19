package com.doneasy.don.repository.user;

import com.doneasy.don.domain.user.Organization;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrganizationRepository {

    List<Organization> findAll();

    Organization findById(Long id);

    Organization findByOrganizationId(String organization_id);

    Organization findByNickname(String nickname);

    String getNicknameById(Long id);

    Boolean saveOrganization(Organization organization);

    Boolean modifyOrganization(Organization organization);
}
