package com.huawei.productionplanning.service.strategy;

import com.huawei.productionplanning.entity.Model;
import com.huawei.productionplanning.entity.ModelDistribution;
import com.huawei.productionplanning.entity.Project;
import com.huawei.productionplanning.enums.Months;
import com.huawei.productionplanning.enums.PlanningType;
import com.huawei.productionplanning.repository.ModelDistributionRepository;
import com.huawei.productionplanning.repository.ProductionTargetRepository;
import com.huawei.productionplanning.repository.ProjectRepository;
import com.huawei.productionplanning.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class WeeklyCalculationStrategy extends AbstractCalculationStrategy {

    public WeeklyCalculationStrategy(ProjectRepository projectRepository, ModelDistributionRepository distributionRepository, ProductionTargetRepository productionTargetRepository) {
        super(projectRepository, distributionRepository, productionTargetRepository);
    }

    @Override
    protected void calculateForProductionTarget(Project project, Integer productionTargetQuantity, Months month, HashMap<String, HashMap<String, Integer>> resultMap) {
        List<Integer> weeksOfMonths = Utils.getWeeksOfMonth(month);

        for (Integer weekOfMonth : weeksOfMonths) {
            HashMap<String, Integer> modelPartQuantityMap = new HashMap<>();

            for (Model model : project.getModels()) {
                calculateForModel(productionTargetQuantity / 4, weekOfMonth, model, modelPartQuantityMap);
            }

            resultMap.put("Week " + weekOfMonth.toString(), modelPartQuantityMap);
        }
    }

    @Override
    protected ModelDistribution getModelDistribution(Integer periodIndex, Long modelID) {
        return Optional.ofNullable(
                distributionRepository.findModelDistributionByModelAndWeekOfYear(modelID, periodIndex)
        ).orElseGet(() ->
                distributionRepository.findModelDistributionByModelAndPlanningType(modelID, PlanningType.FIXED)
        );
    }
}
