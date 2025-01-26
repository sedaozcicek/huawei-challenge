package com.huawei.productionplanning.service.strategy;

import com.huawei.productionplanning.entity.Model;
import com.huawei.productionplanning.entity.ModelDistribution;
import com.huawei.productionplanning.entity.Project;
import com.huawei.productionplanning.enums.Months;
import com.huawei.productionplanning.enums.PlanningType;
import com.huawei.productionplanning.repository.ModelDistributionRepository;
import com.huawei.productionplanning.repository.ProductionTargetRepository;
import com.huawei.productionplanning.repository.ProjectRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
public class MonthlyCalculationStrategy extends AbstractCalculationStrategy {

    public MonthlyCalculationStrategy(ProjectRepository projectRepository, ModelDistributionRepository distributionRepository, ProductionTargetRepository productionTargetRepository) {
        super(projectRepository, distributionRepository, productionTargetRepository);
    }

    @Override
    protected void calculateForProductionTarget(Project project, Integer productionTargetQuantity, Months month, HashMap<String, HashMap<String, Integer>> resultMap) {
        HashMap<String, Integer> modelPartQuantityMap = new HashMap<>();

        for (Model model : project.getModels()) {
            calculateForModel(productionTargetQuantity, month.ordinal(), model, modelPartQuantityMap);
        }

        resultMap.put(month.name(), modelPartQuantityMap);
    }

    @Override
    protected ModelDistribution getModelDistribution(Integer periodIndex, Long modelID) {
        return Optional.ofNullable(
                distributionRepository.findModelDistributionByModelAndMonth(modelID, Months.values()[periodIndex])
        ).orElseGet(() ->
                distributionRepository.findModelDistributionByModelAndPlanningType(modelID, PlanningType.FIXED)
        );
    }
}
