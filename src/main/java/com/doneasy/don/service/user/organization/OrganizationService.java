package com.doneasy.don.service.user.organization;

import com.doneasy.don.domain.project.Project;
import com.doneasy.don.domain.project.ProjectProposal;
import com.doneasy.don.domain.user.Member;
import com.doneasy.don.domain.user.Organization;
import com.doneasy.don.dto.user.UserLoginDto;
import com.doneasy.don.dto.user.organization.OrganizationDetailDto;
import com.doneasy.don.dto.project.ProjectListDto;
import com.doneasy.don.dto.user.organization.OrganizationGetModifyDto;
import com.doneasy.don.dto.user.organization.OrganizationSavedto;
import com.doneasy.don.exception.user.OrganizationException;
import com.doneasy.don.repository.project.ProjectProposalRepository;
import com.doneasy.don.repository.project.ProjectRepository;
import com.doneasy.don.repository.user.MemberRepository;
import com.doneasy.don.repository.user.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationService {

    @Value("${admin.image.dir}") private String path;

    private final OrganizationRepository organizationRepository;
    private final ProjectRepository projectRepository;
    private final ProjectProposalRepository projectProposalRepository;
    private final MemberRepository memberRepository;
    private final OrganizationImageService organizationImageService;

    public OrganizationDetailDto getOrganizationDetail(String organization_id) throws IOException {
        Organization organization = organizationRepository.findByOrganizationId(organization_id);
        List<Project> projectList = projectRepository.findByOrganizationId(organization.getId());
        int activeCount= 0;
        int doneCount= 0;

        List<ProjectListDto> list = new ArrayList<>();

        for (Project project : projectList) {
            if (project.getStatus().toString().equals("ACTIVE")) {
                activeCount++;
            } else {
                doneCount++;
            }
            ProjectProposal projectProposal = projectProposalRepository.findById(project.getProjectProposal_id());
            list.add(new ProjectListDto(projectProposal.getTitle(), project.getStatus().toString(), project.getId()));

        }

        return new OrganizationDetailDto(organization.getEmail(), organization.getNickname(),
                organization.getPhone_number(), getImage(organization.getImage_name()),
                organization.getIntroduction(), organization.getZipcode(), organization.getAddress(),
                activeCount, doneCount, list);
    }

    public OrganizationGetModifyDto getModifyDto(String organizationId) throws IOException {
        Organization findOrganization = organizationRepository.findByOrganizationId(organizationId);
        byte[] image = getImage(findOrganization.getImage_name());
        return OrganizationGetModifyDto.getInstance(findOrganization, image);
    }

    public byte[] getImage(String imageName) throws IOException {
        File file = new File(path + imageName);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return bytes;
    }

    public void save(OrganizationSavedto organizationSavedto, String imageName) {
        Organization organization = Organization.getOrganization(organizationSavedto, imageName);
        validate(organization);
        organizationRepository.saveOrganization(organization);
    }

    private void validate(Organization organization) {
        String organizationId = organization.getOrganization_id();

        // 값이 존재하는 지 확인
        if (organization.getOrganization_id() == null || organization.getOrganization_id().equals("") || organization.getPassword() == null || organization.getPassword().equals("") || organization.getNickname() == null || organization.getNickname().equals("") ||
                organization.getEmail() == null || organization.getEmail().equals("") || organization.getPhone_number() == null || organization.getPhone_number().equals("")) {
            throw new IllegalArgumentException();
        }

        // 존재하는 데이터베이스에 존재하는 지 확인
        Optional<Member> findMember = memberRepository.findByMemberId(organizationId);
        Organization findOrganization = organizationRepository.findByOrganizationId(organizationId);
        log.info("findMember: {}", findMember);
        log.info("findOrganization: {}", organizationRepository);

        if (findMember.isPresent() || findOrganization != null) {
            throw new OrganizationException(OrganizationException.DUPLICATE_ID);
        }

        // 닉네임이 데이터베이스에 존재하는 지 확인
        String nickname = organization.getNickname();
        Optional<Member> findMemberByNickname = memberRepository.findByNickname(nickname);
        Organization findOrganizationByNickname = organizationRepository.findByNickname(nickname);

        if (findMemberByNickname.isPresent() || findOrganizationByNickname != null) {
            throw new OrganizationException(OrganizationException.DUPLICATE_NICKNAME);
        }
    }
    public Boolean login(UserLoginDto userLoginDto) {
        String organizationId = userLoginDto.getMemberId();
        String password = userLoginDto.getPassword();
        Organization findOrganization = organizationRepository.findByOrganizationId(organizationId);
        if (findOrganization != null && findOrganization.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public void modifyOrganization(OrganizationSavedto organizationSavedto, List<MultipartFile> multipartFileList) throws IOException {
        String organization_id = organizationSavedto.getOrganization_id();
        String nickname = organizationSavedto.getNickname();
        Organization findByOrganizationId = organizationRepository.findByOrganizationId(organization_id);
        if (!findByOrganizationId.getNickname().equals(nickname)) {
            Organization findByNickname = organizationRepository.findByNickname(nickname);
            if (findByNickname != null) throw new OrganizationException(OrganizationException.DUPLICATE_NICKNAME);

        }
        String save;
        if (multipartFileList != null) {
            save = organizationImageService.save(multipartFileList);
        } else {
            save = findByOrganizationId.getImage_name();
        }
        System.out.println(organizationSavedto);
        Organization organization = Organization.getOrganization(organizationSavedto, save);
        organizationRepository.modifyOrganization(organization);
    }
}
