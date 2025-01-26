package com.huawei.productionplanning.service.impl;

import com.huawei.productionplanning.dto.CreateModelDistributionDTO;
import com.huawei.productionplanning.dto.ModelDistributionHistoryDTO;
import com.huawei.productionplanning.entity.Model;
import com.huawei.productionplanning.entity.ModelDistribution;
import com.huawei.productionplanning.entity.Project;
import com.huawei.productionplanning.enums.LogLevel;
import com.huawei.productionplanning.enums.Months;
import com.huawei.productionplanning.enums.PlanningType;
import com.huawei.productionplanning.exception.ModelDistributionExceptionNotFound;
import com.huawei.productionplanning.exception.ResourceNotFoundException;
import com.huawei.productionplanning.mapper.LogMapper;
import com.huawei.productionplanning.mapper.ModelDistributionMapper;
import com.huawei.productionplanning.repository.ModelDistributionRepository;
import com.huawei.productionplanning.repository.ProjectRepository;
import com.huawei.productionplanning.service.DistributionService;
import com.huawei.productionplanning.service.LogService;
import com.huawei.productionplanning.utils.Utils;
import com.huawei.productionplanning.validation.DateValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DistributionServiceImpl implements DistributionService {
    private final ModelDistributionRepository distributionRepository;
    private final ModelDistributionMapper modelDistributionMapper;
    private final ProjectRepository projectRepository;
    private final LogService logService;
    private final LogMapper logMapper;


    @Override
    public List<ModelDistributionHistoryDTO> getModelDistributionHistory(
            Long projectId, Months startDate, Months endDate) {

        Project project = projectRepository.findActiveProjectById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        if (!PlanningType.MONTHLY.equals(project.getPlanningType())){
            throw new ModelDistributionExceptionNotFound("Project Type not MONTHLY | Project : " + project.getId());
        }
        List<ModelDistribution> distributions = null;
        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)){
            DateValidation.compareDateIsBefore(Utils.mapToMonths(project.getEndDate().getMonth()),endDate);
            DateValidation.checkStartAndEndDate(startDate,endDate);
            distributions = distributionRepository
                    .findModelDistributionsByDateRange(project.getId(),startDate,endDate,project.getPlanningType());
        }
        else {
            List<Model> modelsByProjectIdOrderByPercentageDesc = distributionRepository.findModelsByProjectIdOrderByPercentageDesc(project.getId(), project.getPlanningType());

            distributions = modelsByProjectIdOrderByPercentageDesc.stream()
                    .flatMap(model -> model.getDistributions().stream())
                    .filter(distribution -> distribution.getPlanningType() == project.getPlanningType())
                    .sorted(Comparator.comparing(ModelDistribution::getMonth)
                            .thenComparing(Comparator.comparing(ModelDistribution::getPercentage).reversed()))
                    .collect(Collectors.toList());
        }

        return distributions.stream()
                .map(this::convertToHistoryDTO)
                .collect(Collectors.toList());
    }
    private ModelDistributionHistoryDTO convertToHistoryDTO(ModelDistribution distribution) {
        return ModelDistributionHistoryDTO.builder()
                .modelName(distribution.getModel().getName())
                .month(distribution.getMonth())
                .percentage(distribution.getPercentage())
                .build();
    }

    public void createDistribution(Long modelId, PlanningType type, CreateModelDistributionDTO distributionDTO) {
        modelDistributionMapper.convertModelDistribution(modelId,type,distributionDTO);
    }

    @Override
    public void deleteDistribution(Long id) {
        ModelDistribution modelDistribution = distributionRepository.findDistribution(id).orElseThrow(() -> new ResourceNotFoundException("Model not found"));
        distributionRepository.delete(modelDistribution);
        String format = String.format("Soft deleted modelDistribution : {}", modelDistribution.getId());
        logService.createLog(logMapper.convertToDTO(format, LogLevel.INFO));
    }

}