package com.huawei.productionplanning.service;

import com.huawei.productionplanning.dto.CreateModelPartDTO;
import com.huawei.productionplanning.dto.UpdateModelPartDTO;

public interface ModelPartService {
    void createModelPart(CreateModelPartDTO createModelPartDTO);

    void updateModelPart(UpdateModelPartDTO modelPartDTO);
}
