package com.huawei.productionplanning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.huawei.productionplanning.enums.Months;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelDistributionHistoryDTO {
    private Months month;
    private String modelName;
    private Double percentage;
}