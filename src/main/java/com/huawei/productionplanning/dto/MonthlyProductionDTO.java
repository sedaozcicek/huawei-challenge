package com.huawei.productionplanning.dto;

import com.huawei.productionplanning.entity.Part;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyProductionDTO {
    private YearMonth yearMonth;
    private Map<Part, Integer> partProduction;
}
