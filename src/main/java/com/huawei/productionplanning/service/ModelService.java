package com.huawei.productionplanning.service;

import com.huawei.productionplanning.dto.CreateModelDTO;
import com.huawei.productionplanning.entity.Project;

public interface ModelService {
    void createModel(CreateModelDTO modelDTO, Project project);
    void updateModelStatus(Long modelId, boolean isActive);

    void deleteModel(Long modelId);
}
