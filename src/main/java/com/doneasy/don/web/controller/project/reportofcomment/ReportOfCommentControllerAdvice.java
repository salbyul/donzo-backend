package com.doneasy.don.web.controller.project.reportofcomment;

import com.doneasy.don.exception.ErrorResult;
import com.doneasy.don.exception.project.reportofcomment.ReportOfCommentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ReportOfCommentControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult reportOfCommentException(ReportOfCommentException e) {
        if (e.getMessage().equals(ReportOfCommentException.DUPLICATE)) {
            return new ErrorResult(ReportOfCommentException.DUPLICATE, ReportOfCommentException.DUPLICATE_CODE);
        }
        if (e.getMessage().equals(ReportOfCommentException.INVALID_ACCESS)) {
            return new ErrorResult(ReportOfCommentException.INVALID_ACCESS, ReportOfCommentException.INVALID_ACCESS_CODE);
        }
        return null;
    }

}
