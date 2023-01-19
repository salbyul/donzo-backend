package com.doneasy.don.service.project.projectproposal;

import com.doneasy.don.domain.project.ContentOfProject;
import com.doneasy.don.domain.project.ProjectProposal;
import com.doneasy.don.domain.user.Organization;
import com.doneasy.don.dto.project.contentofproject.ContentOfProjectSaveDto;
import com.doneasy.don.dto.project.projectproposal.ProjectProposalSaveDto;
import com.doneasy.don.repository.project.ContentOfProjectRepository;
import com.doneasy.don.repository.project.ProjectProposalRepository;
import com.doneasy.don.repository.user.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectProposalService {


    private final ProjectProposalRepository projectProposalRepository;

    private final OrganizationRepository organizationRepository;
    private final ContentOfProjectRepository contentOfProjectRepository;

    @Value("${admin.image.dir}")
    private String path;

    @Value("${admin.image.dir}")
    private String path2;

    public void save(ProjectProposalSaveDto projectProposalSaveDto, List<MultipartFile> multipartFileList, Organization organization) throws IOException {
        ProjectProposal instance = ProjectProposal.getInstance(projectProposalSaveDto, organization.getId());
        List<ContentOfProjectSaveDto> contentOfProjectSaveDtoList = projectProposalSaveDto.getContentOfProjectSaveDtoList();

        //인설트 쿼리문을 날려서 db 에 저장한것.
        projectProposalRepository.saveProject(instance);

        //db 에 sql 을 날려서 결과값을 가져온다.
        ProjectProposal recent = projectProposalRepository.findRecentByTitle(instance.getTitle());
        log.info("title: {}", recent.getTitle());

        //편하게 쓸려고 담음
        Long id = recent.getId();
        log.info("id: {}", id);
        System.out.println(recent.getOrganization_id());
        for (int i = 0; i < multipartFileList.size(); i++) {
            MultipartFile multipartFile = multipartFileList.get(i);

            StringTokenizer st = new StringTokenizer(multipartFile.getOriginalFilename(), ".");
            st.nextToken();
            if (st.nextToken().equals("pdf")) {
                multipartFile.transferTo(new File(path2 + organizationRepository.getNicknameById(recent.getOrganization_id()) + ".pdf"));
                multipartFileList.remove(i);
                break;

            }
        }


        for (int i = 0; i < multipartFileList.size(); i++) {
            MultipartFile multipartFile = multipartFileList.get(i);

            String imageName = multipartFile.getOriginalFilename();
            log.info("imageName: {}", imageName);
            log.info("contentofProject: {}", contentOfProjectSaveDtoList.get(i));

            ContentOfProject contentOfProject = ContentOfProject.getContentOfProject(contentOfProjectSaveDtoList.get(i), i + 1, imageName, id);
            contentOfProjectRepository.saveContent(contentOfProject);
            multipartFile.transferTo(new File(path + imageName));
        }


    }

}