package com.doneasy.don.web.controller.admin;

import com.doneasy.don.exception.ErrorResult;
import com.doneasy.don.exception.admin.AdminException;
import com.doneasy.don.exception.user.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.doneasy.don.web.controller.admin")
public class AdminNoticeControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({AdminException.class, MemberException.class})
    public ErrorResult adminException(AdminException adminException, MemberException memberException) {
        if (adminException.getMessage().equals(AdminException.INVALID_ACCESS)) {
            log.error("INVALID ACCESS");
            return new ErrorResult(AdminException.INVALID_ACCESS, AdminException.INVALID_ACCESS_CODE);
        }
        if (memberException.getMessage().equals(MemberException.INVALID)) {
            log.error("INVALID ACCESS");
            return new ErrorResult(MemberException.INVALID, MemberException.INVALID_CODE);
        }
        return null;
    }
}
