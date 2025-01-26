package com.huawei.productionplanning.service.impl;

import com.huawei.productionplanning.dto.ProductionTargetDTO;
import com.huawei.productionplanning.entity.ProductionTarget;
import com.huawei.productionplanning.entity.Project;
import com.huawei.productionplanning.enums.Months;
import com.huawei.productionplanning.exception.DateException;
import com.huawei.productionplanning.exception.ResourceNotFoundException;
import com.huawei.productionplanning.repository.ProductionTargetRepository;
import com.huawei.productionplanning.repository.ProjectRepository;
import com.huawei.productionplanning.service.ProductionTargetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductionTargetServiceImpl implements ProductionTargetService {
    private final ProjectRepository projectRepository;
    private final ProductionTargetRepository productionTargetRepository;


    @Override
    public void creationProductionTarget(Months month, ProductionTargetDTO productionTargetDTO) {
        Project project = projectRepository.findActiveProjectById(productionTargetDTO.getProjectId()).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        if (!canAddMonth(month, project.getEndDate())) {
            throw new DateException(String.format("The project end date is in %s. You cannot create a target quantity for a date later than this month.", project.getEndDate()));
        }
        ProductionTarget productionTarget = ProductionTarget.builder()
                .project(project)
                .month(month)
                .productionTargetQuantity(productionTargetDTO.getProductionTargetQuantity())
                .build();
        log.info("Creating new ProductionTarget for Project: {}", productionTarget.getProject().getName());
        productionTargetRepository.save(productionTarget);

    }


    public static boolean canAddMonth(Months month, LocalDate endDate) {
        Month projectEndMonth = endDate.getMonth();
        int projectEndYear = endDate.getYear();

        int monthOrder = month.ordinal() + 1;
        if (projectEndYear > endDate.getYear()) {
            return true;
        }
        return monthOrder <= projectEndMonth.getValue();
    }
}

