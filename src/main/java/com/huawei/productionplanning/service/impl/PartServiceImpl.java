package com.huawei.productionplanning.service.impl;

import com.huawei.productionplanning.dto.PartDTO;
import com.huawei.productionplanning.entity.Part;
import com.huawei.productionplanning.enums.LogLevel;
import com.huawei.productionplanning.mapper.LogMapper;
import com.huawei.productionplanning.repository.PartRepository;
import com.huawei.productionplanning.service.LogService;
import com.huawei.productionplanning.service.PartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final LogService logService;
    private final LogMapper logMapper;
    @Override
    public void createPart(PartDTO partDTO) {
        Part part = Part.builder()
                .name(partDTO.getName())
                .code(partDTO.getCode())
                .build();

        partRepository.save(part);

        String format = String.format("Creating new Part: %s", part.getName());
        logService.createLog(logMapper.convertToDTO(format, LogLevel.INFO));
    }
}
