package com.huawei.productionplanning.service;

import com.huawei.productionplanning.dto.LogDTO;
import com.huawei.productionplanning.enums.LogLevel;

import java.time.LocalDateTime;
import java.util.List;

public interface LogService {
    void createLog(LogDTO logDTO);

    List<LogDTO> getLogs(String name, LogLevel logLevel, LocalDateTime startDate, LocalDateTime endDate);
}
