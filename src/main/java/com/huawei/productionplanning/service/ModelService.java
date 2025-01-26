package com.huawei.productionplanning.service;

import com.huawei.productionplanning.dto.CreateModelDTO;
import com.huawei.productionplanning.dto.ModelDistributionDTO;
import com.huawei.productionplanning.entity.ModelDistribution;
import com.huawei.productionplanning.entity.ModelPart;
import com.huawei.productionplanning.entity.Project;
import com.huawei.productionplanning.enums.PlanningType;

import java.util.List;

public interface ModelService {
    void createModel(CreateModelDTO modelDTO, Project project);
    void updateModelStatus(Long modelId, boolean isActive);

    void deleteModel(Long modelId);
}
