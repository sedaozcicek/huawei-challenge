package com.huawei.productionplanning.service.impl;

import com.huawei.productionplanning.entity.Project;
import com.huawei.productionplanning.exception.ResourceNotFoundException;
import com.huawei.productionplanning.repository.ProjectRepository;
import com.huawei.productionplanning.service.ProductionCalculationService;
import com.huawei.productionplanning.service.strategy.CalculationStrategySelector;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductionCalculationServiceImpl implements ProductionCalculationService {
    private final ProjectRepository projectRepository;

    private final CalculationStrategySelector calculationStrategySelector;

    @Override
    @Transactional
    public HashMap<String, HashMap<String, Integer>> generateReportProjectAllMonths(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return calculationStrategySelector.getStrategy(project.getPlanningType()).calculateForProject(project);
    }

}