package com.doneasy.don.web.jwt;

import com.doneasy.don.exception.ErrorResult;
import com.doneasy.don.exception.jwt.JwtTokenException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class JwtTokenControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MalformedJwtException.class)
    public ErrorResult invalidToken(JwtTokenException e) {
        return new ErrorResult(JwtTokenException.INVALID_TOKEN, JwtTokenException.INVALID_TOKEN_CODE);
    }
}
