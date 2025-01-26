package com.huawei.productionplanning.service;


import com.huawei.productionplanning.enums.PlanningType;

import java.util.HashMap;

public interface ProductionCalculationService {
    HashMap<String, HashMap<String , Integer>> generateReportProjectAllMonths(Long projectId);

}
