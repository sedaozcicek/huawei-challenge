package com.huawei.productionplanning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelStatisticsDTO {
    private double average;
    private double minimum;
    private double maximum;
    private double standardDeviation;
}