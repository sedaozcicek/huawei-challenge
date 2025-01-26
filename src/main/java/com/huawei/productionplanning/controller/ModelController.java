package com.huawei.productionplanning.controller;

import com.huawei.productionplanning.dto.CreateModelDTO;
import com.huawei.productionplanning.dto.ModelDistributionDTO;
import com.huawei.productionplanning.entity.Project;
import com.huawei.productionplanning.enums.PlanningType;
import com.huawei.productionplanning.service.ModelService;
import com.huawei.productionplanning.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/models")
@RequiredArgsConstructor
public class ModelController {
    private final ModelService modelService;
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createModel(@RequestBody CreateModelDTO modelDTO) {
        Project project = projectService.getProjectModelById(modelDTO.getProjectId());

        if (project == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Project not found");
        }
        modelService.createModel(modelDTO,project);
        return ResponseEntity.status(HttpStatus.CREATED).body("The model was identified successfully.");
    }

    @PutMapping("/{modelId}/status")
    public ResponseEntity<?> updateModelStatus(
            @PathVariable Long modelId,
            @RequestParam boolean isActive) {
        try {
            modelService.updateModelStatus(modelId, isActive);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Can only update status for models in FIXED planning type");
        }
    }

    @DeleteMapping("/{modelId}/delete")
    public ResponseEntity<Void> deleteModel(@PathVariable Long modelId) {
        modelService.deleteModel(modelId);
        return ResponseEntity.ok().build();
    }


}