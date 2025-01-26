package com.huawei.productionplanning.controller;

import com.huawei.productionplanning.enums.PlanningType;
import com.huawei.productionplanning.service.ProductionCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/calculations")
@RequiredArgsConstructor
public class ProductionCalculationController {
    private final ProductionCalculationService calculationService;

    @GetMapping("/projects/{projectId}/all-period")
    public ResponseEntity<?> generateReportAllMonths(
            @PathVariable Long projectId) {

        HashMap<String, HashMap<String , Integer>> modelPartIntegerHashMap = calculationService.generateReportProjectAllMonths(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(modelPartIntegerHashMap);
    }

}