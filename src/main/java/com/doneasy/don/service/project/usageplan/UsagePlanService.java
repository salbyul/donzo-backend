package com.doneasy.don.service.project.usageplan;

import com.doneasy.don.domain.project.UsagePlan;
import com.doneasy.don.dto.project.usageplan.UsagePlanSaveDto;
import com.doneasy.don.repository.project.ProjectProposalRepository;
import com.doneasy.don.repository.project.UsagePlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsagePlanService {

    private final ProjectProposalRepository projectProposalRepository;
    private final UsagePlanRepository usagePlanRepository;

    public void saveUsagePlan(List<UsagePlanSaveDto> usagePlanSaveDtoList, String title) {
        Long id = projectProposalRepository.findRecentByTitle(title).getId();

        for (UsagePlanSaveDto usagePlanSaveDto : usagePlanSaveDtoList) {
            UsagePlan usagePlan = UsagePlan.usagePlan(usagePlanSaveDto, id);
            usagePlanRepository.save(usagePlan);
        }
    }
}
