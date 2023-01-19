package com.doneasy.don.web.jwt;

import com.doneasy.don.exception.ErrorResult;
import com.doneasy.don.exception.jwt.JwtTokenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Order(2)
@WebFilter
public class JwtExceptionHandler extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (MalformedJwtException | ExpiredJwtException e) {
            setErrorResponse(response, JwtTokenException.INVALID_TOKEN_CODE);
        }
    }

    private void setErrorResponse(HttpServletResponse response, String errorCode) {
        log.error("INVALID TOKEN");
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResult errorResult = new ErrorResult(JwtTokenException.INVALID_TOKEN, JwtTokenException.INVALID_TOKEN_CODE);
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResult));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
