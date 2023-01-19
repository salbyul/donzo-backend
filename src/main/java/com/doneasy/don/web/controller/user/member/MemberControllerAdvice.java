package com.doneasy.don.web.controller.user.member;

import com.doneasy.don.exception.ErrorResult;
import com.doneasy.don.exception.user.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = {"com.doneasy.don.web.controller.user.member", "com.doneasy.don.web.controller.project.donationofproject"})
public class MemberControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberException.class)
    public ErrorResult duplicateMember(MemberException e) {
        if (e.getMessage().equals(MemberException.DUPLICATE_ID)) {
            log.error("DUPLICATE MEMBER");
            return new ErrorResult(MemberException.DUPLICATE_ID, MemberException.DUPLICATE_ID_CODE);
        } else if (e.getMessage().equals(MemberException.DUPLICATE_NICKNAME)) {
            log.error("DUPLICATE NICKNAME");
            return new ErrorResult(MemberException.DUPLICATE_NICKNAME, MemberException.DUPLICATE_NICKNAME_CODE);
        } else if (e.getMessage().equals(MemberException.INVALID)) {
            log.error("INVALID MEMBER");
            return new ErrorResult(MemberException.INVALID, MemberException.INVALID_CODE);
        }
        return null;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult invalidMember(IllegalArgumentException e) {
        log.error("INVALID MEMBER");
        return new ErrorResult(MemberException.INVALID, MemberException.INVALID_CODE);
    }
}
