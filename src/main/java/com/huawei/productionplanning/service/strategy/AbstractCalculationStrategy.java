package com.huawei.productionplanning.service.strategy;

import com.huawei.productionplanning.entity.*;
import com.huawei.productionplanning.enums.Months;
import com.huawei.productionplanning.enums.PlanningType;
import com.huawei.productionplanning.exception.ModelDistributionExceptionNotFound;
import com.huawei.productionplanning.repository.ModelDistributionRepository;
import com.huawei.productionplanning.repository.ProductionTargetRepository;
import com.huawei.productionplanning.repository.ProjectRepository;
import com.huawei.productionplanning.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.util.*;

@Component
@RequiredArgsConstructor
public abstract class AbstractCalculationStrategy {
    private final ProjectRepository projectRepository;
    protected final ModelDistributionRepository distributionRepository;
    private final ProductionTargetRepository productionTargetRepository;

    public HashMap<String, HashMap<String, Integer>> calculateForProject(Project project) {
        HashMap<String, HashMap<String, Integer>> resultMap = new HashMap<>();

        for (ProductionTarget productionTarget : project.getProductionTargets()) {
            if (Objects.nonNull(productionTarget)) {
                Months month = productionTarget.getMonth();

                calculateForProductionTarget(project, productionTarget.getProductionTargetQuantity(), month, resultMap);
            }
        }
        return resultMap;
    }

    protected abstract void calculateForProductionTarget(Project project, Integer productionTargetQuantity, Months month, HashMap<String, HashMap<String, Integer>> resultMap);

    protected void calculateForModel(Integer productionTargetQuantity, Integer periodIndex, Model model, HashMap<String, Integer> modelPartQuantityMap) {
        ModelDistribution modelDistribution = getModelDistribution(periodIndex, model.getId());
        if (Objects.isNull(modelDistribution)) {
            throw new ModelDistributionExceptionNotFound("Model distribution not found for model: " + model.getId());
        }

        Integer modelQuantity = Math.round((float) ((productionTargetQuantity) * modelDistribution.getPercentage()) / 100);
        for (ModelPart modelPart : model.getModelParts()) {
            Integer count = modelPartQuantityMap.getOrDefault(modelPart.getPart().getCode(), 0);
            int totalModelPartQuantity = calculateModelPartQuantity(modelQuantity, modelPart, count);
            modelPartQuantityMap.put(modelPart.getPart().getCode(), totalModelPartQuantity);
        }
    }

    protected abstract ModelDistribution getModelDistribution(Integer periodIndex, Long modelID);

    protected int calculateModelPartQuantity(Integer modelQuantity, ModelPart modelPart, Integer count) {
        Integer modelPartQuantity = modelPart.getQuantity() * modelQuantity;
        return count + modelPartQuantity;
    }
}

