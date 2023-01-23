package com.doneasy.don.web.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@WebFilter(urlPatterns = {"/organization/detail", "/organization/modify", "/organization/group/get", "/member/detail", "/member/modify", "/member/personal/get", "/support-of-project/save-support", "/report-of-comment/save-report", "/project-review/save", "/project-proposal/save", "/donation-of-project/pay/ready/*", "/comment-of-project/save-comment", "/donation-of-project/pay/completed", "/donation-of-project/pay/get-result", "/can-write/*", "/admin/is-admin"})
public class JwtFilter implements Filter {

    private final JwtTokenService jwtTokenService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("JWTFILTER INIT!!!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("JWTFILTER");
        HttpServletRequest req = (HttpServletRequest) request;
        String uuid = UUID.randomUUID().toString();
        request.setAttribute("uuid", uuid);
        if (req.getMethod().equals("OPTIONS")) {
            log.info("OPTIONS");
            log.info("[REQUEST] : {}", uuid);
            chain.doFilter(request, response);
            log.info("not");
        } else {
            log.info("[REQUEST]: {}", uuid);
            String authorizationHeader = req.getHeader("Authorization");
            log.info("header: {}", authorizationHeader);
            Claims claims = jwtTokenService.parseJwtTokenByHeader(authorizationHeader);
            Map<String, Object> payload = claims;
            jwtTokenService.validationAuthorizationHeader(claims);
            if (payload.get("target") != null && payload.get("target").equals("member")) {
                log.info("memberId setting");
                request.setAttribute("memberId", payload.get("member"));
            } else if (payload.get("target") != null && payload.get("target").equals("organization")) {
                log.info("organizationId setting");
                request.setAttribute("organizationId", payload.get("organization"));
            }
            Date expiration = claims.getExpiration();
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
