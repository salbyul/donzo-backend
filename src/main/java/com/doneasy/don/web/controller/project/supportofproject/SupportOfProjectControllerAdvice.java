package com.doneasy.don.web.controller.project.supportofproject;

import com.doneasy.don.exception.ErrorResult;
import com.doneasy.don.exception.project.supportofproject.SupportOfProjectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SupportOfProjectControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SupportOfProjectException.class)
    public ErrorResult duplicateSupport(SupportOfProjectException e) {
        if (e.getMessage().equals(SupportOfProjectException.DUPLICATED)) {
            log.error("DUPLICATE SUPPORT");
            return new ErrorResult(SupportOfProjectException.DUPLICATED, SupportOfProjectException.DUPLICATED_CODE);
        }
        if (e.getMessage().equals(SupportOfProjectException.OVERFLOW_PRICE)) {
            log.error("OVERFLOW PRICE");
            return new ErrorResult(SupportOfProjectException.OVERFLOW_PRICE, SupportOfProjectException.OVERFLOW_PRICE_CODE);
        }
        if (e.getMessage().equals(SupportOfProjectException.INVALID_ACCESS)) {
            log.error("INVALID ACCESS");
            return new ErrorResult(SupportOfProjectException.INVALID_ACCESS, SupportOfProjectException.INVALID_ACCESS_CODE);
        }
        return null;
    }
}
