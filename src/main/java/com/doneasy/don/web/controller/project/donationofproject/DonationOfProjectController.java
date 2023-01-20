package com.doneasy.don.web.controller.project.donationofproject;

import com.doneasy.don.dto.project.donationofproject.DonationOfProjectShowDto;
import com.doneasy.don.exception.project.donationofproject.DonationOfProjectException;
import com.doneasy.don.repository.project.DonationOfProjectRepository;
import com.doneasy.don.service.admin.project.ProjectService;
import com.doneasy.don.service.project.donationofproject.DonationOfProjectService;
import com.doneasy.don.service.project.donationofproject.kakao.KakaoApproveResponse;
import com.doneasy.don.service.project.donationofproject.kakao.KakaoPayService;
import com.doneasy.don.service.project.donationofproject.kakao.KakaoReadyResponse;
import com.doneasy.don.service.project.donationofproject.kakao.KakaoSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/donation-of-project")
@RequiredArgsConstructor
@SessionAttributes({"tid", "projectId"})
public class DonationOfProjectController {

    private final DonationOfProjectRepository donationOfProjectRepository;
    private final KakaoPayService kakaoPayService;
    private final KakaoSession kakaoSession;
    private final DonationOfProjectService donationOfProjectService;
    private final ProjectService projectService;


    @PostMapping("/get-donation-price")
    public ResponseEntity<List<DonationOfProjectShowDto>> getDonation() {
        List<DonationOfProjectShowDto> list = donationOfProjectRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PostMapping("/get-donation")
    public ResponseEntity<List<DonationOfProjectShowDto>> getDonator(HttpServletRequest req, HttpServletResponse res, Long id) {

        List<DonationOfProjectShowDto> donationOfProjectList = donationOfProjectRepository.findAllById(id);

        return new ResponseEntity<>(donationOfProjectList, HttpStatus.OK);
    }

    @PostMapping("/pay/ready/{value}")
    public ResponseEntity<KakaoReadyResponse> pay(@PathVariable Integer value, HttpServletRequest request, @RequestBody HashMap<String, Object> map) {
        if (value == 0) throw new DonationOfProjectException(DonationOfProjectException.ZERO_PRICE);
        String memberId = (String) request.getAttribute("memberId");
        Long projectId = Long.parseLong((String) map.get("projectId"));
        donationOfProjectService.validate(memberId, projectId, value);
        KakaoReadyResponse kakaoReadyResponse = kakaoPayService.readyToPay(value, memberId, projectId);
        return new ResponseEntity(kakaoReadyResponse, HttpStatus.OK);
    }

    @PostMapping("/pay/completed")
    public ResponseEntity payCompleted(@RequestBody HashMap<String, Object> map, HttpServletRequest request) {
        String memberId = (String) request.getAttribute("memberId");
        String pgToken = (String) map.get("pgToken");
        KakaoApproveResponse kakaoApproveResponse = kakaoPayService.payApprove(pgToken, memberId);
        donationOfProjectService.save(memberId);
        kakaoPayService.saveResult(memberId, KakaoPayService.SUCCESS);
        log.info("SUCCESS");
        return new ResponseEntity(kakaoApproveResponse, HttpStatus.OK);
    }

    @GetMapping("/pay/cancel")
    public ResponseEntity payCancel(HttpServletRequest request) {
        String memberId = (String) request.getAttribute("memberId");
        kakaoPayService.saveResult(memberId, KakaoPayService.CANCEL);
        log.info("CANCEL");
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/pay/fail")
    public ResponseEntity payFail(@RequestBody HashMap<String, Object> map, HttpServletRequest request) {
        String memberId = (String) request.getAttribute("memberId");
        kakaoPayService.saveResult(memberId, KakaoPayService.FAIL);
        log.info("FAIL");
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/pay/get-result")
    public ResponseEntity payResult(HttpServletRequest request) throws InterruptedException {
        String memberId = (String) request.getAttribute("memberId");
        log.info("memberId want to get result: {}", memberId);
        projectService.updateProjectByDonationResult(kakaoSession.getProjectId(memberId), kakaoSession.getValue(memberId));
        String result = kakaoPayService.getResult(memberId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
