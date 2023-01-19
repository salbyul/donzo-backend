package com.doneasy.don.service.project.reportofcomment;

import com.doneasy.don.domain.project.ReportOfComment;
import com.doneasy.don.domain.user.Member;
import com.doneasy.don.exception.project.reportofcomment.ReportOfCommentException;
import com.doneasy.don.exception.user.MemberException;
import com.doneasy.don.repository.project.ReportOfCommentRepository;
import com.doneasy.don.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportOfCommentService {

    private final ReportOfCommentRepository reportOfCommentRepository;
    private final MemberRepository memberRepository;

    public void save(ReportOfComment reportOfComment, String memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if (optionalMember.isEmpty()) throw new ReportOfCommentException(ReportOfCommentException.INVALID_ACCESS);
        Member member = optionalMember.get();
        Long commentId = reportOfComment.getCommentOfProject_id();
        ReportOfComment findReportOfComment = reportOfCommentRepository.findByCommentIdAndMemberId(member.getId(), commentId);

        if (findReportOfComment != null) throw new ReportOfCommentException(ReportOfCommentException.DUPLICATE);

        reportOfCommentRepository.saveReportOfComment(reportOfComment);
    }
}
