package com.huawei.productionplanning.service;


import java.util.HashMap;

public interface ProductionCalculationService {
    HashMap<String, HashMap<String , Integer>> generateReportProjectAllMonths(Long projectId);

}
