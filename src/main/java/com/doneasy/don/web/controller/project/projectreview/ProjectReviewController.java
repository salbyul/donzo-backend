package com.doneasy.don.web.controller.project.projectreview;

import com.doneasy.don.domain.project.ContentOfProject;
import com.doneasy.don.domain.project.ContentOfProjectReview;
import com.doneasy.don.domain.project.ProjectReview;
import com.doneasy.don.domain.project.UsagePlan;
import com.doneasy.don.dto.project.commentofproject.CommentOfProjectShowDto;
import com.doneasy.don.dto.project.contentofproject.ContentOfProjectShowDto;
import com.doneasy.don.dto.project.donationofproject.DonationOfProjectShowDto;
import com.doneasy.don.dto.project.project.ProjectShowDto;
import com.doneasy.don.dto.project.projectreview.ProjectReviewSaveDto;
import com.doneasy.don.dto.project.supportofproject.SupportOfProjectShowDto;
import com.doneasy.don.exception.project.projectreview.ProjectReviewException;
import com.doneasy.don.repository.project.*;
import com.doneasy.don.service.project.contentofproject.ContentOfProjectService;
import com.doneasy.don.service.project.projectreview.ProjectReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/project-review")
public class ProjectReviewController {

    private final ProjectReviewService projectReviewService;
    private final ProjectRepository projectRepository;
    private final ContentOfProjectReviewRepository contentOfProjectReviewRepository;
    private final CommentOfProjectRepository commentOfProjectRepository;
    private final SupportOfProjectRepository supportOfProjectRepository;
    private final ContentOfProjectService contentOfProjectService;
    private final DonationOfProjectRepository donationOfProjectRepository;
    private final ProjectReviewRepository projectReviewRepository;


    @PostMapping("/save")
    public ResponseEntity saveReview(@RequestPart("boardSaveReDto") ProjectReviewSaveDto projectReviewSaveDto, @RequestPart(value = "imageList", required = false) List<MultipartFile> multipartFileList, HttpServletRequest request) throws IOException {
        String organizationId = (String) request.getAttribute("organizationId");
        projectReviewService.save(projectReviewSaveDto, multipartFileList, organizationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/can-write/{id}")
    public ResponseEntity canWrite(@PathVariable Long id) {

        ProjectReview findProjectReview = projectReviewRepository.findProjectReviewByProjectId(id);
        if (findProjectReview != null) {
            throw new ProjectReviewException(ProjectReviewException.DUPLICATE);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/get-project")
    public ResponseEntity getProject(HttpServletRequest req, HttpServletResponse res, Long projectId) {
        System.out.println(projectId);
        ProjectShowDto findProjectInfo = projectRepository.findProjectReviewInfoByProjectId(projectId);

        return new ResponseEntity(findProjectInfo, HttpStatus.OK);
    }

    @PostMapping("/get-content")
    public ResponseEntity<List<ContentOfProject>> getContent(HttpServletRequest req, HttpServletResponse res, Long projectId) throws IOException {
        log.info("id: {}", projectId);

        List<ContentOfProjectReview> findContent = contentOfProjectReviewRepository.findContentForProjectReview(projectId);
        log.info("contentList Size: {}", findContent.size());

        List<String> imageNameList = new ArrayList<>();
        for (int i = 0; i < findContent.size(); i++) {
            String imageName = findContent.get(i).getImage_name();
            if (imageName == null) continue;
            imageNameList.add(findContent.get(i).getImage_name());
        }

        List<byte[]> array = contentOfProjectService.getByteArray(imageNameList);
        log.info("image Size: {}", array.size());
        List<ContentOfProjectShowDto> list = new ArrayList<>();
        int imageIndex = 0;
        for (ContentOfProjectReview contentOfProjectReview : findContent) {
            if (contentOfProjectReview.getContents() == null && contentOfProjectReview.getSubtitle() == null) continue;
            if (contentOfProjectReview.getContents().equals("") && contentOfProjectReview.getSubtitle().equals("")) continue;
            list.add(new ContentOfProjectShowDto(contentOfProjectReview.getId(), contentOfProjectReview.getSubtitle(), contentOfProjectReview.getContents(), array.get(imageIndex++), contentOfProjectReview.getOrder_num()));
        }

        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping("/get-comment")
    public ResponseEntity getComment(HttpServletRequest req, HttpServletResponse res, Long projectId) {
//        System.out.println(id);
        List<CommentOfProjectShowDto> showComment = commentOfProjectRepository.findAllById(projectId);
        return new ResponseEntity(showComment, HttpStatus.OK);
    }

    @PostMapping("/get-support")
    public ResponseEntity getSupport(HttpServletRequest req, HttpServletResponse res, Long projectId) {
//        System.out.println(id);
        List<SupportOfProjectShowDto> supportOfProjectList = supportOfProjectRepository.findShowDtoByProjectId(projectId);
        return new ResponseEntity(supportOfProjectList, HttpStatus.OK);
    }

    @PostMapping("/get-donation")
    public ResponseEntity getDonator(HttpServletRequest req, HttpServletResponse res, Long projectId) {

        List<DonationOfProjectShowDto> donationOfProjectList = donationOfProjectRepository.findAllById(projectId);

        return new ResponseEntity(donationOfProjectList, HttpStatus.OK);
    }


}
