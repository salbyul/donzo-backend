package com.doneasy.don.web.controller.admin;

import com.doneasy.don.domain.project.ContentOfProject;
import com.doneasy.don.domain.project.ProjectProposal;
import com.doneasy.don.domain.user.Organization;
import com.doneasy.don.dto.admin.project.ProjectListDto;
import com.doneasy.don.dto.admin.project.ProjectProposalDetailDto;
import com.doneasy.don.dto.admin.project.ProjectProposalListDto;
import com.doneasy.don.repository.project.ContentOfProjectRepository;
import com.doneasy.don.repository.project.ProjectProposalRepository;
import com.doneasy.don.repository.project.ProjectRepository;
import com.doneasy.don.repository.user.OrganizationRepository;
import com.doneasy.don.service.admin.project.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminProjectController {

    private final ProjectService projectService;
    private final ProjectProposalRepository projectProposalRepository;
    private final ProjectRepository projectRepository;
    private final OrganizationRepository organizationRepository;
    private final ContentOfProjectRepository contentOfProjectRepository;

    @GetMapping("/projectproposal")
    public ResponseEntity findAllProjectProposal(@RequestParam(required = false, name = "s") String search) {
        if (search == null) {
            List<ProjectProposalListDto> findAll = projectService.findAll();
            return new ResponseEntity(findAll, HttpStatus.OK);
        }
        List<ProjectProposalListDto> findByTitle = projectService.findByTitle(search);
        return new ResponseEntity(findByTitle, HttpStatus.OK);
    }

    @PostMapping("/projectproposal/success")
    public ResponseEntity success(@RequestBody HashMap<String, Object> map) {
        Long id = Long.valueOf((Integer) map.get("id"));
        Boolean success = projectProposalRepository.modifyStatusSuccess(Long.valueOf(id));
        ProjectProposal findProjectProposal = projectProposalRepository.findById(id);
        Long organization_id = findProjectProposal.getOrganization_id();
        if (success) {
            projectService.save(findProjectProposal.getId(), organization_id);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/projectproposal/fail")
    public ResponseEntity fail(@RequestBody HashMap<String, Object> map) {
        Long id = Long.valueOf((Integer) map.get("id"));
        projectProposalRepository.modifyStatusFail(id);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/project")
    public ResponseEntity<List<ProjectListDto>> findAllProject(@RequestParam(required = false, name = "s") String search) {
        if (search == null) {
            List<ProjectListDto> projectShowList = projectService.getProjectShowList();
            return new ResponseEntity<>(projectShowList, HttpStatus.OK);
        }

        List<ProjectListDto> projectShowList = projectService.getProjectShowListByTitle(search);
        return new ResponseEntity<>(projectShowList, HttpStatus.OK);
    }

    @PostMapping("/project/status/done")
    public ResponseEntity statusToDone(@RequestBody HashMap<String, Object> map) {
        Long id = Long.valueOf((Integer) map.get("id"));
        projectRepository.statusToDone(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/projectproposal/detail/{id}")
    public ResponseEntity<ProjectProposal> getDetail(@PathVariable Long id) {
        ProjectProposal findProjectProposal = projectProposalRepository.findById(id);
        Organization findOrganization = organizationRepository.findById(findProjectProposal.getOrganization_id());
        List<ContentOfProject> findContentOfProjectList = contentOfProjectRepository.findByProjectProposalId(findProjectProposal.getId());
        ProjectProposalDetailDto projectProposalDetailDto = new ProjectProposalDetailDto(findProjectProposal.getTitle(), findProjectProposal.getCategory() == null ? null : findProjectProposal.getCategory().toString(), findProjectProposal.getService_start_date().toString(), findProjectProposal.getService_end_date().toString(), findProjectProposal.getTarget_price(), findProjectProposal.getCreated_date().toString(), findProjectProposal.getDeadline().toString(), findOrganization.getNickname(), findProjectProposal.getStatus().toString(), findContentOfProjectList);

        return new ResponseEntity(projectProposalDetailDto, HttpStatus.OK);
    }

}
