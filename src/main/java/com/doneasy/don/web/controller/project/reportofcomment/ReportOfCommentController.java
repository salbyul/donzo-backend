package com.doneasy.don.web.controller.project.reportofcomment;

import com.doneasy.don.domain.project.ReportOfComment;
import com.doneasy.don.service.project.reportofcomment.ReportOfCommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/report-of-comment")
public class ReportOfCommentController {

    private final ReportOfCommentService reportofCommentService;

    @PostMapping("/save-report")
    public ResponseEntity saveReportOfComment (@RequestBody ReportOfComment reportOfComment, HttpServletRequest request) {
        String memberId = (String) request.getAttribute("memberId");
        log.info("memberId: {}", memberId);
        reportofCommentService.save(reportOfComment, memberId);
        return ResponseEntity.ok().build();
    }
}
