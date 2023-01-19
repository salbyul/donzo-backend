package com.doneasy.don.web.controller.project.supportofproject;

import com.doneasy.don.domain.project.Project;
import com.doneasy.don.domain.project.ProjectProposal;
import com.doneasy.don.dto.project.supportofproject.SupportOfProjectShowDto;
import com.doneasy.don.exception.project.supportofproject.SupportOfProjectException;
import com.doneasy.don.repository.project.ProjectProposalRepository;
import com.doneasy.don.repository.project.ProjectRepository;
import com.doneasy.don.repository.project.SupportOfProjectRepository;
import com.doneasy.don.service.admin.project.ProjectService;
import com.doneasy.don.service.project.projectlist.ProjectListService;
import com.doneasy.don.service.project.supportofproject.SupportOfProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/support-of-project")
public class SupportOfProjectController {

    private final SupportOfProjectRepository supportOfProjectRepository;
    private final SupportOfProjectService supportOfProjectService;
    private final ProjectListService projectListService;
    private final ProjectProposalRepository projectProposalRepository;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    @PostMapping("/save-support")
    public ResponseEntity saveSupport(@RequestBody HashMap<String, Long> map, HttpServletRequest request) {
        String organizationId = (String) request.getAttribute("organizationId");
        if (organizationId != null) throw new SupportOfProjectException(SupportOfProjectException.INVALID_ACCESS);
        String memberId = (String) request.getAttribute("memberId");
        Long project_id = map.get("project_id");
        if (memberId == null) throw new SupportOfProjectException(SupportOfProjectException.INVALID_ACCESS);
        Integer donationResult = projectListService.getDonationResult(project_id);
        Project findProject = projectRepository.findById(project_id);
        ProjectProposal findProjectProposal = projectProposalRepository.findById(findProject.getProjectProposal_id());
        Integer target_price = findProjectProposal.getTarget_price();
        if (target_price < donationResult + 100)
            throw new SupportOfProjectException(SupportOfProjectException.OVERFLOW_PRICE);

        supportOfProjectService.save(project_id, memberId);
        projectService.updateProjectByDonationResult(project_id, null);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/get-support")
    public ResponseEntity getSupport(HttpServletRequest req, HttpServletResponse res, Long id) {
//        System.out.println(id);
        List<SupportOfProjectShowDto> supportOfProjectList = supportOfProjectRepository.findShowDtoByProjectId(id);
        return new ResponseEntity(supportOfProjectList, HttpStatus.OK);
    }
}
