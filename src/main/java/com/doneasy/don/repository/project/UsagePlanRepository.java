package com.doneasy.don.repository.project;

import com.doneasy.don.domain.project.UsagePlan;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsagePlanRepository {
    List<UsagePlan> findUsagePlanById(int id);

    Boolean save(UsagePlan usagePlan);

}
