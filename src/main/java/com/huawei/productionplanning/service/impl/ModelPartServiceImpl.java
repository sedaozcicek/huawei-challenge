package com.huawei.productionplanning.service.impl;

import com.huawei.productionplanning.dto.CreateModelPartDTO;
import com.huawei.productionplanning.dto.UpdateModelPartDTO;
import com.huawei.productionplanning.entity.Model;
import com.huawei.productionplanning.entity.ModelPart;
import com.huawei.productionplanning.entity.Part;
import com.huawei.productionplanning.enums.LogLevel;
import com.huawei.productionplanning.exception.ResourceNotFoundException;
import com.huawei.productionplanning.mapper.LogMapper;
import com.huawei.productionplanning.repository.ModelPartRepository;
import com.huawei.productionplanning.repository.ModelRepository;
import com.huawei.productionplanning.repository.PartRepository;
import com.huawei.productionplanning.service.LogService;
import com.huawei.productionplanning.service.ModelPartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ModelPartServiceImpl implements ModelPartService {
    private final ModelRepository modelRepository;
    private final ModelPartRepository modelPartRepository;
    private final PartRepository partRepository;
    private final LogService logService;
    private final LogMapper logMapper;

    @Override
    public void createModelPart(CreateModelPartDTO createModelPartDTO) {
        Model model = modelRepository.findById(createModelPartDTO.getModelId())
                .orElseThrow(() -> new ResourceNotFoundException("Model not found"));

        Part part = partRepository.findById(createModelPartDTO.getPartId()).orElseThrow(() -> new ResourceNotFoundException("Part not found"));
        ModelPart modelPart = ModelPart.builder()
                .model(model)
                .part(part)
                .quantity(createModelPartDTO.getQuantity())
                .build();

        modelPartRepository.save(modelPart);
        String format = String.format("Creating new modelPart for Model and Part: {} - {}", model.getName(),part.getName());
        logService.createLog(logMapper.convertToDTO(format, LogLevel.INFO));
    }

    @Override
    public void updateModelPart(UpdateModelPartDTO modelPartDTO) {
        ModelPart modelPart = modelPartRepository.findModelPartByPartAndModel(modelPartDTO.getPartId(),modelPartDTO.getModelId()).orElseThrow(() -> new ResourceNotFoundException("modelPart not found"));
        modelPart.setQuantity(modelPartDTO.getQuantity());
        modelPartRepository.save(modelPart);
        String format = String.format("Updating modelPart for Model %s - Part %s Quantity :%s", modelPart.getModel().getName(),modelPart.getPart().getName(),modelPartDTO.getQuantity());
        logService.createLog(logMapper.convertToDTO(format, LogLevel.INFO));
    }
}
