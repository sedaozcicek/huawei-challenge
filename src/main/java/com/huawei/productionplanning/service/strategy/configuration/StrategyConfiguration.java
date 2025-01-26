package com.huawei.productionplanning.service.strategy.configuration;

import com.huawei.productionplanning.enums.PlanningType;
import com.huawei.productionplanning.repository.ModelDistributionRepository;
import com.huawei.productionplanning.repository.ProductionTargetRepository;
import com.huawei.productionplanning.repository.ProjectRepository;
import com.huawei.productionplanning.service.strategy.AbstractCalculationStrategy;
import com.huawei.productionplanning.service.strategy.FixedCalculationStrategy;
import com.huawei.productionplanning.service.strategy.MonthlyCalculationStrategy;
import com.huawei.productionplanning.service.strategy.WeeklyCalculationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class StrategyConfiguration {
    @Bean
    public HashMap<PlanningType, AbstractCalculationStrategy> strategyMap(
            ProjectRepository projectRepository,
            ModelDistributionRepository distributionRepository,
            ProductionTargetRepository productionTargetRepository) {
        HashMap<PlanningType, AbstractCalculationStrategy> map = new HashMap<>();
        map.put(PlanningType.MONTHLY, new MonthlyCalculationStrategy(projectRepository, distributionRepository, productionTargetRepository));
        map.put(PlanningType.WEEKLY, new WeeklyCalculationStrategy(projectRepository, distributionRepository, productionTargetRepository));
        map.put(PlanningType.FIXED, new FixedCalculationStrategy(projectRepository, distributionRepository, productionTargetRepository));
        return map;
    }
}
