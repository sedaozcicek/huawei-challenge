package com.huawei.productionplanning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelWithPartsDTO {
    @JsonProperty("name")
    private String modelName;
    private List<PartProductionDTO> modelParts;
}
