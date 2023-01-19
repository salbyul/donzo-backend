package com.doneasy.don.web.controller.project.commentofproject;

import com.doneasy.don.exception.ErrorResult;
import com.doneasy.don.exception.project.commentofproject.CommentOfProjectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.doneasy.don.web.controller.project.commentofproject")
public class CommentOfProjectControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CommentOfProjectException.class)
    public ErrorResult duplicateMember(CommentOfProjectException e) {
        if (e.getMessage().equals(CommentOfProjectException.DUPLICATED)) {
            log.error("DUPLICATE COMMENT");
            return new ErrorResult(CommentOfProjectException.DUPLICATED, CommentOfProjectException.DUPLICATED_CODE);
        } else if (e.getMessage().equals(CommentOfProjectException.INVALID_ACCESS)) {
            log.error("INVALID ACCESS COMMENT");
            return new ErrorResult(CommentOfProjectException.INVALID_ACCESS, CommentOfProjectException.INVALID_ACCESS_CODE);
        }
        return null;
    }
}
