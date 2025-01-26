package com.huawei.productionplanning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.huawei.productionplanning.entity.Project;
import com.huawei.productionplanning.enums.PlanningType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelDTO {
    private String name;
    private Project project;
    private PlanningType planningType;
}