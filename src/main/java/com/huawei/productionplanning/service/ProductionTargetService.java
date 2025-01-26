package com.huawei.productionplanning.service;

import com.huawei.productionplanning.dto.ProductionTargetDTO;
import com.huawei.productionplanning.entity.Project;
import com.huawei.productionplanning.enums.Months;
import com.huawei.productionplanning.enums.PlanningType;

public interface ProductionTargetService {
    void creationProductionTarget(Months month,ProductionTargetDTO productionTargetDTO);
}
