package com.doneasy.don.service.project.donationofproject.kakao;

import com.doneasy.don.domain.project.Project;
import com.doneasy.don.domain.project.ProjectProposal;
import com.doneasy.don.domain.user.Member;
import com.doneasy.don.exception.user.MemberException;
import com.doneasy.don.repository.project.ProjectProposalRepository;
import com.doneasy.don.repository.project.ProjectRepository;
import com.doneasy.don.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoPayService {

    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final ProjectProposalRepository projectProposalRepository;
    private final KakaoSession kakaoSession;

    public static final String SUCCESS = "SUCCESS";
    public static final String CANCEL = "CANCEL";
    public static final String FAIL = "FAIL";
    public static final String TIME_OUT = "TIMEOUT";

    @Value("${kakao.admin.key}")
    private String adminKey;

    public KakaoReadyResponse readyToPay(Integer value, String memberId, Long projectId) {
        String url = "https://kapi.kakao.com/v1/payment/ready";
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Project findProject = projectRepository.findById(projectId);
        ProjectProposal findProjectProposal = projectProposalRepository.findById(findProject.getProjectProposal_id());
        if (optionalMember.isEmpty()) throw new MemberException(MemberException.INVALID);
        Member member = optionalMember.get();
        String orderId = member.getMember_id() + "::" + findProject.getId();
        String itemName = findProjectProposal.getTitle();

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("cid", "TC0ONETIME");
        map.add("partner_order_id", orderId);
        map.add("partner_user_id", member.getMember_id());
        map.add("item_name", itemName);
        map.add("quantity", "1");
        map.add("total_amount", value.toString());
        map.add("tax_free_amount", "0");
        map.add("approval_url", "http://localhost:3000/kakaopay/approve");
        map.add("cancel_url", "http://localhost:3000//kakaopay/cancel");
        map.add("fail_url", "http://localhost:3000/kakaopay/fail");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, getHeaders());

        RestTemplate restTemplate = new RestTemplate();
        KakaoReadyResponse kakaoReadyResponse = restTemplate.postForObject(url, requestEntity, KakaoReadyResponse.class);
        kakaoReadyResponse.setMemberId(memberId);
        kakaoReadyResponse.setProjectId(projectId);
        log.info("결제준비 응답객체: {}", kakaoReadyResponse);
        kakaoSession.saveTid(memberId, kakaoReadyResponse.getTid());
        kakaoSession.saveProjectId(memberId, projectId);
        kakaoSession.saveOrderId(memberId, orderId);
        kakaoSession.saveValue(memberId, value);
        return kakaoReadyResponse;
    }

    public KakaoApproveResponse payApprove(String pgToken, String memberId) {

        String url = "https://kapi.kakao.com/v1/payment/approve";
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if (optionalMember.isEmpty()) throw new MemberException(MemberException.INVALID);
        Member member = optionalMember.get();
        String orderId = kakaoSession.getOrderId(memberId);
        String tid = kakaoSession.getTid(memberId);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("cid", "TC0ONETIME");
        map.add("tid", tid);
        map.add("partner_order_id", orderId);
        map.add("partner_user_id", member.getMember_id());
        map.add("pg_token", pgToken);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, getHeaders());

        RestTemplate restTemplate = new RestTemplate();
        KakaoApproveResponse kakaoApproveResponse = restTemplate.postForObject(url, requestEntity, KakaoApproveResponse.class);
        log.info("결제승인 응답객체: {}", kakaoApproveResponse);

        return kakaoApproveResponse;
    }

    public String getResult(String memberId) throws InterruptedException {
        String result = kakaoSession.getResult(memberId);
        LocalDateTime now = LocalDateTime.now();
        while (result == null) {
//            log.info("now: {}", now);
//            Thread.sleep(100L);
            result = kakaoSession.getResult(memberId);
//            log.info("real Now: {}", LocalDateTime.now());
        }
        kakaoSession.invalidData(memberId);
        return result;
    }

    public void saveResult(String memberId, String status) {
        kakaoSession.saveResult(memberId, status);
    }


    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + adminKey);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return headers;
    }
}
