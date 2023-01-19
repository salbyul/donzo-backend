package com.doneasy.don.web.controller.project.projectreview;


import com.doneasy.don.exception.ErrorResult;
import com.doneasy.don.exception.project.projectreview.ProjectReviewException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ProjectReviewControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProjectReviewException.class)
    public ErrorResult projectReviewException(ProjectReviewException e) {
        if (e.getMessage().equals(ProjectReviewException.DUPLICATE)) {
            log.error("PROJECT-REVIEW DUPLICATE");
            return new ErrorResult(ProjectReviewException.DUPLICATE, ProjectReviewException.DUPLICATE_CODE);
        }
        return null;
    }
}
