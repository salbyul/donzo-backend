package com.doneasy.don.web.controller.project.commentofproject;

import com.doneasy.don.domain.project.CommentOfProject;
import com.doneasy.don.dto.project.commentofproject.CommentOfProjectSaveDto;
import com.doneasy.don.dto.project.commentofproject.CommentOfProjectShowDto;
import com.doneasy.don.exception.project.commentofproject.CommentOfProjectException;
import com.doneasy.don.repository.project.CommentOfProjectRepository;
import com.doneasy.don.service.admin.project.ProjectService;
import com.doneasy.don.service.project.commentofproject.CommentOfProjectService;
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
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/comment-of-project")
public class CommentOfProjectController {

    private final CommentOfProjectRepository commentOfProjectRepository;
    private final CommentOfProjectService commentOfProjectService;
    private final ProjectService projectService;

    @PostMapping("/get-comment")
    public ResponseEntity getComment(HttpServletRequest req, HttpServletResponse res, Long id) {
//        System.out.println(id);
        List<CommentOfProjectShowDto> showComment = commentOfProjectRepository.findAllById(id);
        return new ResponseEntity(showComment, HttpStatus.OK);
    }

    @PostMapping("/save-comment")
    public ResponseEntity saveComment(@RequestBody CommentOfProjectSaveDto commentOfProjectSaveDto, HttpServletRequest request) {
        log.info("projectId: {}", commentOfProjectSaveDto.getProjectId());
        log.info("content: {}", commentOfProjectSaveDto.getContent());
        String memberId = (String) request.getAttribute("memberId");
        if (memberId == null) throw new CommentOfProjectException(CommentOfProjectException.INVALID_ACCESS);
        commentOfProjectService.save(commentOfProjectSaveDto, memberId);
        projectService.updateProjectByDonationResult(commentOfProjectSaveDto.getProjectId(), null);
        
        return ResponseEntity.ok().build();
    }

}
