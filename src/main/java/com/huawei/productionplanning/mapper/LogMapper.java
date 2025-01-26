package com.huawei.productionplanning.mapper;

import com.huawei.productionplanning.dto.LogDTO;
import com.huawei.productionplanning.enums.LogLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LogMapper {
    public LogDTO convertToDTO(String message, LogLevel logLevel) {
        return LogDTO.builder()
                .level(logLevel)
                .message(message)
                .build();
    }



}
