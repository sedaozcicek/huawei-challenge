package com.huawei.productionplanning.controller;

import com.huawei.productionplanning.dto.CreateModelPartDTO;
import com.huawei.productionplanning.dto.UpdateModelPartDTO;
import com.huawei.productionplanning.service.ModelPartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/model-parts")
@RequiredArgsConstructor
public class ModelPartController {
    private final ModelPartService modelPartService;

    @PostMapping
    public ResponseEntity<?> createModelPart(@RequestBody CreateModelPartDTO createModelPartDTO) {
        modelPartService.createModelPart(createModelPartDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/update-model-part")
    public ResponseEntity<Void> addModelPart(
            @RequestBody UpdateModelPartDTO modelPartDTO) {
        modelPartService.updateModelPart(modelPartDTO);
        return ResponseEntity.ok().build();
    }

}
