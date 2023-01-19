package com.doneasy.don.web.controller.admin;

import com.doneasy.don.domain.admin.notice.Notice;
import com.doneasy.don.domain.user.Grade;
import com.doneasy.don.domain.user.Member;
import com.doneasy.don.dto.admin.notice.NoticeDetailDto;
import com.doneasy.don.dto.admin.notice.NoticeDto;
import com.doneasy.don.dto.admin.notice.NoticeSaveDto;
import com.doneasy.don.exception.admin.AdminException;
import com.doneasy.don.exception.user.MemberException;
import com.doneasy.don.repository.admin.notice.INoticeRepository;
import com.doneasy.don.repository.user.MemberRepository;
import com.doneasy.don.service.admin.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminNoticeController {

    private final NoticeService noticeService;
    private final INoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/is-admin")
    public ResponseEntity adminCheck(HttpServletRequest request) {
        String memberId = (String) request.getAttribute("memberId");
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if (optionalMember.isEmpty()) throw new MemberException(MemberException.INVALID);
        Member member = optionalMember.get();
        Grade grade = member.getGrade();
        if (!grade.equals(Grade.ADMIN)) throw new AdminException(AdminException.INVALID_ACCESS);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/notice")
    public ResponseEntity<List<NoticeDto>> getNoticeList(@RequestParam(required = false, name = "s") String search) {
        if (search == null) {
            return new ResponseEntity(noticeService.getNoticeAsList(), HttpStatus.OK);
        }
        List<Notice> findNoticeList = noticeRepository.findByTitle(search);
        Collections.sort(findNoticeList);
        return new ResponseEntity(findNoticeList, HttpStatus.OK);
    }

    /**
     * @RequestBody JSON 형식의 데이터를 객체에 매핑해준다.
     */
    @PostMapping(value = "/notice/create", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity save(@RequestPart("noticeSaveDto") NoticeSaveDto noticeSaveDto, @RequestPart(required = false) List<MultipartFile> fileList) throws IOException {
        noticeService.save(noticeSaveDto, fileList);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/notice/detail/{id}")
    public ResponseEntity<NoticeDetailDto> getDetail(@PathVariable Long id) throws IOException {
        NoticeDetailDto detail = noticeService.getDetail(id);
        return new ResponseEntity<>(detail, HttpStatus.OK);
    }

    @DeleteMapping("/notice/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Boolean aBoolean = noticeRepository.deleteById(id);
        System.out.println(aBoolean);
        return new ResponseEntity(HttpStatus.OK);
    }
}
