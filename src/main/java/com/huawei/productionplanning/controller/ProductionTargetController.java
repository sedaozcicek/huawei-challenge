package com.huawei.productionplanning.controller;

import com.huawei.productionplanning.dto.ProductionTargetDTO;
import com.huawei.productionplanning.enums.Months;
import com.huawei.productionplanning.service.ProductionTargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/production-targets/{month}")
@RequiredArgsConstructor
public class ProductionTargetController {
    private final ProductionTargetService productionTargetService;
    @PostMapping
    public ResponseEntity<?> createModel(@PathVariable Months month, @RequestBody ProductionTargetDTO productionTargetDTO) {
        productionTargetService.creationProductionTarget(month,productionTargetDTO);
        return ResponseEntity.ok().build();
    }
}
