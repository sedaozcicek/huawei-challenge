package com.huawei.productionplanning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateModelPartDTO {
    private Long partId;
    private Long modelId;
    private Integer quantity;
}
