package com.huawei.productionplanning.mapper;

import com.huawei.productionplanning.dto.CreateModelDistributionDTO;
import com.huawei.productionplanning.entity.Model;
import com.huawei.productionplanning.entity.ModelDistribution;
import com.huawei.productionplanning.enums.LogLevel;
import com.huawei.productionplanning.enums.PlanningType;
import com.huawei.productionplanning.exception.ModelAlreadyExistException;
import com.huawei.productionplanning.exception.ResourceNotFoundException;
import com.huawei.productionplanning.repository.ModelDistributionRepository;
import com.huawei.productionplanning.repository.ModelRepository;
import com.huawei.productionplanning.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
@Slf4j
@RequiredArgsConstructor
public class ModelDistributionMapper {
    private final ModelRepository modelRepository;
    private final ModelDistributionRepository distributionRepository;
    private final LogService logService;
    private final LogMapper logMapper;

    public void convertModelDistribution(Long modelId, PlanningType type, CreateModelDistributionDTO distributionDTO) {
        Model model = modelRepository.findActiveByModelId(modelId).orElseThrow(() -> new ResourceNotFoundException("Model not found"));

        switch (type) {
            case MONTHLY -> {
                ModelDistribution modelDistributionByModelAndMonth = distributionRepository.findModelDistributionByModelAndMonth(modelId, distributionDTO.getMonth());
                if (Objects.nonNull(modelDistributionByModelAndMonth)){
                    String format = String.format("This ModelDistribution is exist. ModelDistribution id : %s", modelDistributionByModelAndMonth.getId());
                    logService.createLog(logMapper.convertToDTO(format, LogLevel.ERROR));
                    throw new ModelAlreadyExistException("This ModelDistribution is exist.");
                }
                setMonthlyDistribution(ModelDistribution.builder()
                        .model(model)
                        .month(distributionDTO.getMonth()), distributionDTO,type);

            }
            case WEEKLY -> {
                ModelDistribution modelDistributionByModelAndWeekOfYear = distributionRepository.findModelDistributionByModelAndWeekOfYear(modelId, distributionDTO.getWeekOfYear());
                if (Objects.nonNull(modelDistributionByModelAndWeekOfYear)){
                    String format = String.format("This ModelDistribution is exist. ModelDistribution id : %s", modelDistributionByModelAndWeekOfYear.getId());
                    logService.createLog(logMapper.convertToDTO(format, LogLevel.ERROR));
                    throw new ModelAlreadyExistException("This ModelDistribution is exist.");
                }
                setMonthlyDistribution(ModelDistribution.builder()
                        .model(model)
                        .weekOfYear(distributionDTO.getWeekOfYear()), distributionDTO,type);
            }

            case FIXED -> {
                ModelDistribution modelDistributionByModelAndPlanningType = distributionRepository.findModelDistributionByModelAndPlanningType(modelId, PlanningType.FIXED);
                if (Objects.nonNull(modelDistributionByModelAndPlanningType)){
                    String format = String.format("This ModelDistribution is exist. ModelDistribution id : %s", modelDistributionByModelAndPlanningType.getId());
                    logService.createLog(logMapper.convertToDTO(format, LogLevel.ERROR));
                    throw new ModelAlreadyExistException("A model with the given ID already exists: " + model.getId());                }
                setMonthlyDistribution(ModelDistribution.builder()
                        .model(model), distributionDTO,type);
            }
        }

    }

    private void setMonthlyDistribution(ModelDistribution.ModelDistributionBuilder model, CreateModelDistributionDTO distributionDTO, PlanningType type) {
        ModelDistribution modelDistribution = model
                .percentage(distributionDTO.getPercentage())
                .planningType(type)
                .build();
        distributionRepository.save(modelDistribution);
        String format = String.format("Creating new modelPart for ModelPart", modelDistribution.getId());
        logService.createLog(logMapper.convertToDTO(format, LogLevel.INFO));
    }
}
