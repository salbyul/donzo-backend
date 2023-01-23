package com.doneasy.don.web.jwt;

import com.doneasy.don.exception.jwt.JwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@Service
public class JwtTokenService {

    @Value("${secretkey}")
    private String secretkey;

    // 토큰 발급
    public String publishAccessToken(String memberId, String organizationId) {
        Date date = new Date();
        HashMap<String, Object> map = new HashMap<>();
        if (memberId != null && organizationId == null) {
            map.put("member", memberId);
            map.put("target", "member");
            return Jwts.builder()
                    .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더에 JWT 토큰임을 표시
                    .setIssuer("donzo") // 토큰 발급자 donzo로 설정
                    .setIssuedAt(date) // 발급 일자 현재 시간
                    .setExpiration(new Date(date.getTime() + Duration.ofMinutes(30).toMillis())) // 만료시간 현재 시간 + 30분
                    .setClaims(map) // 담을 정보 아이디
                    .signWith(SignatureAlgorithm.HS256, secretkey) // 전자서명
                    .compact();
        }
        if (memberId == null && organizationId != null) {
            map.put("organization", organizationId);
            map.put("target", "organization");
            return Jwts.builder()
                    .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더에 JWT 토큰임을 표시
                    .setIssuer("donzo") // 토큰 발급자 donzo로 설정
                    .setIssuedAt(date) // 발급 일자 현재 시간
                    .setExpiration(new Date(date.getTime() + Duration.ofMinutes(30).toMillis())) // 만료시간 현재 시간 + 30분
                    .setClaims(map) // 담을 정보 아이디
                    .signWith(SignatureAlgorithm.HS256, secretkey) // 전자서명
                    .compact();
        }
        return null;
    }

    public String publishRefreshToken() {
        return null;
    }

    public Claims parseJwtTokenByHeader(String authorizationHeader) {
        validationAuthorizationHeader(authorizationHeader);
        String token = extractToken(authorizationHeader);
        return Jwts.parser()
                .setSigningKey(secretkey) // secretkey를 넣어 토큰 해석
                .parseClaimsJws(token) // 해석할 토큰 주입
                .getBody();
    }

    public Claims parseJwtToken(String token) {
        log.info("token: {}", token);
        return Jwts.parser()
                .setSigningKey(secretkey) // secretkey를 넣어 토큰 해석
                .parseClaimsJws(token) // 해석할 토큰 주입
                .getBody();
    }

    // 헤더 토큰 검증
    private void validationAuthorizationHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new JwtTokenException(JwtTokenException.INVALID_TOKEN);
        }
    }

    // 헤더 문자열 분리
    private String extractToken(String token) {
        return token.substring("Bearer ".length());
    }

    // 토큰에서 개인아이디 분리
    public String extractMemberId(String token) {
        return (String) parseJwtToken(token).get("member");
    }

    // 토큰에서 단체아이디 분리
    public String extractOrganizationId(String token) {
        return (String) parseJwtToken(token).get("organization");
    }
}
