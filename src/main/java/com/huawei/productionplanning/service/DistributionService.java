package com.huawei.productionplanning.service;

import com.huawei.productionplanning.dto.CreateModelDistributionDTO;
import com.huawei.productionplanning.dto.ModelDistributionHistoryDTO;
import com.huawei.productionplanning.enums.Months;
import com.huawei.productionplanning.enums.PlanningType;

import java.util.List;

public interface DistributionService {
    List<ModelDistributionHistoryDTO> getModelDistributionHistory(
            Long modelId, Months startDate, Months endDate);
    void createDistribution(Long projectId, PlanningType type, CreateModelDistributionDTO distributionDTO);

    void deleteDistribution(Long distributionId);
}
