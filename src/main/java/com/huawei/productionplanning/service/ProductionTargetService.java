package com.huawei.productionplanning.service;

import com.huawei.productionplanning.dto.ProductionTargetDTO;
import com.huawei.productionplanning.enums.Months;

public interface ProductionTargetService {
    void creationProductionTarget(Months month,ProductionTargetDTO productionTargetDTO);
}
