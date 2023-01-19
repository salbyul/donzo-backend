package com.doneasy.don.web.controller.project.project;

import com.doneasy.don.dto.project.project.ProjectShowDto;
import com.doneasy.don.repository.project.ProjectRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private final ProjectRepository projectRepository;

    @PostMapping("/get-project")
    public ResponseEntity getProject(HttpServletRequest req, HttpServletResponse res, Long id) {
//        System.out.println(id);
        ProjectShowDto findProjectInfo = projectRepository.findProjectInfoByProjectId(id);

        return new ResponseEntity(findProjectInfo, HttpStatus.OK);
    }
}
