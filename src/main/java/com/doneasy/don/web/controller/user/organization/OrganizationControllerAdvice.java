package com.doneasy.don.web.controller.user.organization;

import com.doneasy.don.dto.user.organization.OrganizationSavedto;
import com.doneasy.don.exception.ErrorResult;
import com.doneasy.don.exception.user.OrganizationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.doneasy.don.web.controller.user.organization")
public class OrganizationControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OrganizationException.class)
    public ErrorResult duplicateMember(OrganizationException e) {
        String message = e.getMessage();
        if (message.equals(OrganizationException.DUPLICATE_ID)) {
            log.error("DUPLICATE ORGANIZATION");
            return new ErrorResult(OrganizationException.DUPLICATE_ID, OrganizationException.DUPLICATE_ID_CODE);
        } else if (message.equals(OrganizationException.DUPLICATE_NICKNAME)) {
            log.error("DUPLICATE ORGANIZATION NICKNAME");
            return new ErrorResult(OrganizationException.DUPLICATE_NICKNAME, OrganizationException.DUPLICATE_NICKNAME_CODE);
        }
        return null;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult invalidMember(IllegalArgumentException e) {
        log.error("INVALID ORGANIZATION");
        return new ErrorResult(OrganizationException.INVALID, OrganizationException.INVALID_CODE);
    }
}
