package com.huawei.productionplanning.service.strategy;

import com.huawei.productionplanning.enums.PlanningType;
import com.huawei.productionplanning.repository.ModelDistributionRepository;
import com.huawei.productionplanning.repository.ProductionTargetRepository;
import com.huawei.productionplanning.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class CalculationStrategySelector {
    private final ProjectRepository projectRepository;
    private final ModelDistributionRepository distributionRepository;
    private final ProductionTargetRepository productionTargetRepository;
    private final HashMap<PlanningType, AbstractCalculationStrategy> strategyMap;

    public AbstractCalculationStrategy getStrategy(PlanningType planningType) {
        return strategyMap.get(planningType);
    }

}
