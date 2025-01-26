package com.huawei.productionplanning.service.strategy;

import com.huawei.productionplanning.entity.ModelDistribution;
import com.huawei.productionplanning.enums.PlanningType;
import com.huawei.productionplanning.repository.ModelDistributionRepository;
import com.huawei.productionplanning.repository.ProductionTargetRepository;
import com.huawei.productionplanning.repository.ProjectRepository;
import org.springframework.stereotype.Component;

@Component
public class FixedCalculationStrategy extends AbstractCalculationStrategy {

    public FixedCalculationStrategy(ProjectRepository projectRepository, ModelDistributionRepository distributionRepository, ProductionTargetRepository productionTargetRepository) {
        super(projectRepository, distributionRepository, productionTargetRepository);
    }

    @Override
    protected ModelDistribution getModelDistribution(Integer periodIndex, Long modelID) {
        return distributionRepository.findModelDistributionByModelAndPlanningType(modelID, PlanningType.FIXED);
    }
}
