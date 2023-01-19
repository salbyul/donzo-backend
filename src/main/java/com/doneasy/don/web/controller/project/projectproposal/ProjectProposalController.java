package com.doneasy.don.web.controller.project.projectproposal;

import com.doneasy.don.domain.user.Organization;
import com.doneasy.don.dto.project.projectlist.ProjectListShowDto;
import com.doneasy.don.dto.project.projectproposal.ProjectProposalSaveDto;
import com.doneasy.don.dto.project.usageplan.UsagePlanSaveDto;
import com.doneasy.don.repository.user.OrganizationRepository;
import com.doneasy.don.service.project.projectlist.ProjectListService;
import com.doneasy.don.service.project.projectproposal.ProjectProposalService;
import com.doneasy.don.service.project.usageplan.UsagePlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/project-proposal")
public class ProjectProposalController {

    private final ProjectProposalService projectProposalService;
    private final ProjectListService projectListService;
    private final UsagePlanService usagePlanService;
    private final OrganizationRepository organizationRepository;

    @PostMapping("/save")
    public ResponseEntity save(@RequestPart("boardSaveDto") ProjectProposalSaveDto projectProposalSaveDto, @RequestPart("imageList") List<MultipartFile> multipartFileList, @RequestPart("usagePlan") List<UsagePlanSaveDto> usagePlanSaveDtoList, HttpServletRequest request) throws IOException {
        String organizationId = (String) request.getAttribute("organizationId");
        log.info("organizationId: {}", organizationId);
        Organization findOrganization = organizationRepository.findByOrganizationId(organizationId);
        projectProposalService.save(projectProposalSaveDto, multipartFileList, findOrganization);
        usagePlanService.saveUsagePlan(usagePlanSaveDtoList, projectProposalSaveDto.getTitle());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/find-all-donate/{tag}")
    public ResponseEntity getDonatingProjectBoard(@PathVariable String tag) throws IOException {
//        System.out.println("tag:" + tag);

        List<ProjectListShowDto> list = projectListService.findByTagDonating(tag);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping("/find-all-donated/{tag}")
    public ResponseEntity getDonatedProjectBoard(@PathVariable String tag) throws IOException {
        System.out.println("tag:" + tag);
        List<ProjectListShowDto> list = projectListService.findByTagDonated(tag);

        return new ResponseEntity(list, HttpStatus.OK);
    }
}
