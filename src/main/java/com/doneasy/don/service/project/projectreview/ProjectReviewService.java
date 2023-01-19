package com.doneasy.don.service.project.projectreview;

import com.doneasy.don.domain.project.ContentOfProjectReview;
import com.doneasy.don.domain.project.ProjectReview;
import com.doneasy.don.domain.user.Organization;
import com.doneasy.don.dto.project.contentofprojectreview.ContentOfProjectReviewSaveDto;
import com.doneasy.don.dto.project.projectreview.ProjectReviewSaveDto;
import com.doneasy.don.repository.project.ContentOfProjectReviewRepository;
import com.doneasy.don.repository.project.ProjectReviewRepository;
import com.doneasy.don.repository.user.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectReviewService {

    private final ProjectReviewRepository projectReviewRepository;
    private final ContentOfProjectReviewRepository contentOfProjectReviewRepository;
    private final OrganizationRepository organizationRepository;


    // 모금 후기 경로
    @Value("${admin.image.dir}")
    private String path;

    public void save(ProjectReviewSaveDto projectReviewSaveDto, List<MultipartFile> multipartFileList, String organizationId) throws IOException {
        Organization findOrganization = organizationRepository.findByOrganizationId(organizationId);
        ProjectReview instance = ProjectReview.getInstance(projectReviewSaveDto, findOrganization.getId());
        List<ContentOfProjectReviewSaveDto> contentReDtoList = projectReviewSaveDto.getContentOfProjectReviewSaveDtoList();

        projectReviewRepository.saveReview(instance);
        log.info("title: {}", projectReviewSaveDto.getTitle());
        ProjectReview recent = projectReviewRepository.findReviewRecentByTitle(instance.getTitle());
        Long id = recent.getId();

        for (int i = 0; i < contentReDtoList.size(); i++) {
            String imageName = null;

            if (multipartFileList != null && multipartFileList.size() >= i + 1) {
                MultipartFile multipartFile = multipartFileList.get(i);
                if (multipartFile != null) {
                    imageName = multipartFile.getOriginalFilename();
                    multipartFile.transferTo(new File(path + imageName));
                }
            }
            ContentOfProjectReview contentOfProject2 = ContentOfProjectReview.getContentOfProjectReview(contentReDtoList.get(i), i + 1, imageName, id);
            contentOfProjectReviewRepository.saveContentOfProjectReview(contentOfProject2);
        }
    }
}
