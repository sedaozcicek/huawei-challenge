package com.huawei.productionplanning.dto;

import com.huawei.productionplanning.enums.LogLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogDTO {

    private String message;
    private LogLevel level;
    private LocalDateTime createdAt;
}
