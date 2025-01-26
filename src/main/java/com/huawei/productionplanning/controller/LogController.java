package com.huawei.productionplanning.controller;

import com.huawei.productionplanning.dto.LogDTO;
import com.huawei.productionplanning.enums.LogLevel;
import com.huawei.productionplanning.service.LogService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;


    @GetMapping("{message}/{level}/{startDate}/{endDate}")
    public ResponseEntity<?> getLogByName(
            @RequestParam(required = false) String message,
            @RequestParam(required = false) LogLevel level,
            @Parameter(
                    description = "Start date in ISO-8601 format (e.g., 2024-12-31T00:00:00)",
                    example = "2024-12-31T00:00:00",
                    schema = @Schema(type = "string", format = "date-time")
            )
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")  LocalDateTime startDate,
            @Parameter(
                    description = "Start date in ISO-8601 format (e.g., 2024-12-31T00:00:00)",
                    example = "2024-12-31T00:00:00",
                    schema = @Schema(type = "string", format = "date-time")
            )
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime endDate
    ) {
        List<LogDTO> logs = logService.getLogs(message, level, startDate, endDate);
        return ResponseEntity.ok().body(logs);
    }

}
