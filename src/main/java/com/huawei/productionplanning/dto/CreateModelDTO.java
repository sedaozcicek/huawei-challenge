package com.huawei.productionplanning.dto;

import com.huawei.productionplanning.enums.PlanningType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateModelDTO {
    private String name;
    private Long projectId;
}
