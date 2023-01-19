package com.doneasy.don.web.controller.project.donationofproject;

import com.doneasy.don.exception.ErrorResult;
import com.doneasy.don.exception.project.donationofproject.DonationOfProjectException;
import com.doneasy.don.exception.user.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.doneasy.don.web.controller.project.donationofproject")
public class DonationOfProjectControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DonationOfProjectException.class)
    public ErrorResult donationOfProjectException(DonationOfProjectException e) {
        if (e.getMessage().equals(DonationOfProjectException.OVERFLOW_PRICE)) {
            log.error("OVERFLOW PRICE");
            return new ErrorResult(DonationOfProjectException.OVERFLOW_PRICE, DonationOfProjectException.OVERFLOW_PRICE_CODE);
        } else if (e.getMessage().equals(DonationOfProjectException.ZERO_PRICE)) {
            log.error("ZERO PRICE");
            return new ErrorResult(DonationOfProjectException.ZERO_PRICE, DonationOfProjectException.ZERO_PRICE_CODE);
        }
        return null;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberException.class)
    public ErrorResult memberException(MemberException e) {
        if (e.getMessage().equals(MemberException.DUPLICATE_ID)) {
            log.error("DUPLICATE");
            return new ErrorResult(MemberException.DUPLICATE_ID, MemberException.DUPLICATE_ID_CODE);
        }
        return null;
    }
}
