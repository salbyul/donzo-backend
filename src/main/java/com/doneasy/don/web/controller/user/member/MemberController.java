package com.doneasy.don.web.controller.user.member;

import com.doneasy.don.domain.user.Member;
import com.doneasy.don.dto.user.member.MemberDetailDto;
import com.doneasy.don.dto.user.UserLoginDto;
import com.doneasy.don.dto.user.member.MemberModifyDto;
import com.doneasy.don.exception.user.MemberException;
import com.doneasy.don.repository.user.MemberRepository;
import com.doneasy.don.service.user.member.MemberService;
import com.doneasy.don.web.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final JwtTokenService jwtTokenService;

    @GetMapping("/detail")
    public ResponseEntity<MemberDetailDto> memberDetail(HttpServletRequest request) {
        String memberId = (String) request.getAttribute("memberId");
        log.info("memberId: {}", memberId);
        MemberDetailDto memberDetail = memberService.getMemberDetail(memberId);
        return new ResponseEntity<>(memberDetail, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Member member) {
        memberService.save(member);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/modify")
    public ResponseEntity modify(@RequestBody MemberModifyDto memberModifyDto, HttpServletRequest request) {
        String memberId = (String) request.getAttribute("memberId");
        log.info("member: {}", memberModifyDto);
        memberService.modifyMember(memberModifyDto, memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/personal/get")
    public ResponseEntity getPersonal(HttpServletRequest request) {
        String memberId = (String) request.getAttribute("memberId");
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if (optionalMember.isEmpty()) throw new MemberException(MemberException.INVALID);
        Member memberExceptPassword = Member.getMemberExceptPassword(optionalMember.get());
        return new ResponseEntity(memberExceptPassword, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<List<String>> login(@RequestBody UserLoginDto userLoginDto) {
        log.info("memberId: {}", userLoginDto.getMemberId());
        String target = userLoginDto.getTarget();
        if (memberService.login(userLoginDto)) {
            String token;
            if (userLoginDto.getTarget().equals("personal")) {
                token = jwtTokenService.publishAccessToken(userLoginDto.getMemberId(), null);
            } else {
                throw new RuntimeException("ERROR!!!");
            }
            List<String> result = new ArrayList<>();
            result.add(token);
            result.add(target);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
