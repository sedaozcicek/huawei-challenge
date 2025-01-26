package com.huawei.productionplanning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartProductionDTO {
    private String name;
    private String code;
    private Integer requiredQuantity;
}