package com.doneasy.don.web.controller.project.usageplan;

import com.doneasy.don.domain.project.UsagePlan;
import com.doneasy.don.repository.project.UsagePlanRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/usage-plan")
public class UsagePlanController {

    private final UsagePlanRepository usagePlanRepository;

    @PostMapping("get-usageplan")
    public ResponseEntity<List<UsagePlan>> getUsagePlan(HttpServletRequest req, HttpServletResponse res, int id) {
//        System.out.println("id : " + id);
        List<UsagePlan> usagePlanList = usagePlanRepository.findUsagePlanById(id);
        return new ResponseEntity(usagePlanList, HttpStatus.OK);
    }
}
