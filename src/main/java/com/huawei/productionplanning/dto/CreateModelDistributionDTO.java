package com.huawei.productionplanning.dto;

import com.huawei.productionplanning.enums.Months;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateModelDistributionDTO {
    private Months month;
    private Integer WeekOfYear;
    private Double percentage;
}
