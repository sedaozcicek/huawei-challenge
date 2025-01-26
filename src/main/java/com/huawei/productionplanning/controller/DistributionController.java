package com.huawei.productionplanning.controller;

import com.huawei.productionplanning.dto.CreateModelDistributionDTO;
import com.huawei.productionplanning.dto.ModelDistributionHistoryDTO;
import com.huawei.productionplanning.enums.Months;
import com.huawei.productionplanning.enums.PlanningType;
import com.huawei.productionplanning.service.DistributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/distributions")
@RequiredArgsConstructor
public class DistributionController {
    private final DistributionService distributionService;

    @PostMapping("/{modelId}/set-distribution/{type}")
    public ResponseEntity<?> setDistribution(
            @PathVariable Long modelId,
            @PathVariable PlanningType type,
            @RequestBody CreateModelDistributionDTO distributionDTO) {
        try {
            distributionService.createDistribution(modelId,type,distributionDTO);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/projects/{projectId}/history/start-date/{startDate}/end-date/{endDate}")
    public ResponseEntity<List<ModelDistributionHistoryDTO>> getModelDistributionHistory(
            @PathVariable Long projectId,
            @RequestParam(required = false) Months startDate,
            @RequestParam(required = false) Months endDate) {



        List<ModelDistributionHistoryDTO> history =
                distributionService.getModelDistributionHistory(projectId, startDate, endDate);

        return ResponseEntity.ok(history);
    }
    @DeleteMapping("/{distributionId}/delete")
    public ResponseEntity<Void> deletePart(@PathVariable Long distributionId) {
        distributionService.deleteDistribution(distributionId);
        return ResponseEntity.ok().build();
    }




}