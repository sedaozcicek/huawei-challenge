package com.huawei.productionplanning.service.impl;

import com.huawei.productionplanning.dto.LogDTO;
import com.huawei.productionplanning.entity.Log;
import com.huawei.productionplanning.enums.LogLevel;
import com.huawei.productionplanning.repository.LogRepository;
import com.huawei.productionplanning.service.LogService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
private final LogRepository logRepository;
    @Override
    @Transactional
    public void createLog(LogDTO logDTO) {
        Log log = Log.builder()
                .level(logDTO.getLevel())
                .message(logDTO.getMessage())
                .build();

        logRepository.save(log);
    }

    @Override
    public List<LogDTO> getLogs(String message, LogLevel logLevel, LocalDateTime startDate, LocalDateTime endDate) {
        List<LogDTO> log = Collections.emptyList();
        if (StringUtils.isNotBlank(message)) {
            log = getLogByName(message);
        }

        if (Objects.nonNull(logLevel)) {
            log = getLogsByLevel(logLevel);
        }

        if (startDate != null && endDate != null) {
            log = getLogsByTimestampRange(startDate, endDate);
        }
        return log;
    }

    private List<LogDTO> getLogByName(String name) {
        List<Log> logs = logRepository.findLogByMessageContaining(name);
        return getLogDTOs(logs);

    }
    public List<LogDTO> getLogsByLevel(LogLevel level) {
        List<Log> logs = logRepository.findByLevel(level);
        return getLogDTOs(logs);
    }
    public List<LogDTO> getLogsByTimestampRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Log> logs = logRepository.findLogByTimestampBetween(startDate, endDate);
        return getLogDTOs(logs);
    }


    private static List<LogDTO> getLogDTOs(List<Log> logs) {
        if (CollectionUtils.isNotEmpty(logs)){
            return logs.stream()
                    .map(log -> {
                        LogDTO logDTO = new LogDTO();
                        logDTO.setMessage(log.getMessage());
                        logDTO.setLevel(log.getLevel());
                        logDTO.setCreatedAt(log.getCreatedAt());
                        return logDTO;
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }
}