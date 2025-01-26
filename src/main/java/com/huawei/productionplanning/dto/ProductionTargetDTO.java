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
public class ProductionTargetDTO {
    private Long projectId;
    private Integer productionTargetQuantity;
}
