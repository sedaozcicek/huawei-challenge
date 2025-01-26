package com.huawei.productionplanning.service.impl;

import com.huawei.productionplanning.dto.CreateModelDTO;
import com.huawei.productionplanning.entity.Model;
import com.huawei.productionplanning.entity.Project;
import com.huawei.productionplanning.enums.LogLevel;
import com.huawei.productionplanning.enums.PlanningType;
import com.huawei.productionplanning.exception.IllegalOperationException;
import com.huawei.productionplanning.exception.ResourceNotFoundException;
import com.huawei.productionplanning.mapper.LogMapper;
import com.huawei.productionplanning.repository.ModelRepository;
import com.huawei.productionplanning.service.LogService;
import com.huawei.productionplanning.service.ModelService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;
    private final LogService logService;
    private final LogMapper logMapper;
    @Override
    @Transactional
    public void createModel(CreateModelDTO createModelDTO, Project project) {
        Model model = Model.builder()
                .name(createModelDTO.getName())
                .isActive(true)
                .project(project)
                .build();

        modelRepository.save(model);
        String format = String.format("Creating new model: %s", model.getName());
        logService.createLog(logMapper.convertToDTO(format, LogLevel.INFO));
    }

    @Override
    @Transactional
    public void updateModelStatus(Long modelId, boolean isActive) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new ResourceNotFoundException("Model not found"));

        if (!PlanningType.FIXED.equals(model.getProject().getPlanningType())) {
            log.error("Cannot update status for models in {} planning type", model.getProject().getPlanningType());
            throw new IllegalOperationException("Can only update status for models in FIXED planning type");
        }

        model.setIsActive(isActive);
        modelRepository.save(model);
        String format = String.format("Updated model %s status to: %s", model.getName(),isActive);
        logService.createLog(logMapper.convertToDTO(format, LogLevel.INFO));
    }

    @Override
    public void deleteModel(Long modelId) {
        Model model = modelRepository.findActiveByModelId(modelId).orElseThrow(() -> new ResourceNotFoundException("Model not found"));
        modelRepository.delete(model);
        String format = String.format("Soft deleted model : %s", model.getName());
        logService.createLog(logMapper.convertToDTO(format, LogLevel.INFO));
    }

}