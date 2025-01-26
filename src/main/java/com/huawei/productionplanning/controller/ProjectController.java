package com.huawei.productionplanning.controller;

import com.huawei.productionplanning.dto.ModelDTO;
import com.huawei.productionplanning.dto.ProjectDTO;
import com.huawei.productionplanning.entity.Project;
import com.huawei.productionplanning.enums.Months;
import com.huawei.productionplanning.enums.PlanningType;
import com.huawei.productionplanning.exception.ResourceNotFoundException;
import com.huawei.productionplanning.repository.ProjectRepository;
import com.huawei.productionplanning.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectRepository projectRepository;

    @PostMapping("/create/{planningType}")
    public ResponseEntity<?> createProject(@RequestBody ProjectDTO projectDTO,@PathVariable PlanningType planningType) {
        try {
            Project project = projectService.createProject(projectDTO,planningType);
            return ResponseEntity.status(HttpStatus.CREATED).body(project);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Girdiğiniz bilgileri kontrol edin.");
        }
    }

    @GetMapping()
    public ResponseEntity<List<ProjectDTO>> listAllProjects(){
        List<ProjectDTO> projectDTOS = projectService.listProjects();
        if (CollectionUtils.isNotEmpty(projectDTOS)){
            return ResponseEntity.status(HttpStatus.OK).body(projectDTOS);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{projectId}/sort-by-percentile")
    public ResponseEntity<?> getProjectById(@PathVariable Long projectId){
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        if (PlanningType.FIXED.equals(project.getPlanningType())){
            HashMap<String, List<String>> fixedModelNameList = projectService.sortByPlanningType(project);
            return ResponseEntity.status(HttpStatus.OK).body(fixedModelNameList);
        }
        HashMap<String, HashMap<String, List<String>>> modelNameList = projectService.getProjectById(projectId);
        if (!modelNameList.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(modelNameList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project is not found !");
    }

    @PutMapping("/{projectId}/planning-type")
    public ResponseEntity<Void> updatePlanningType(
            @PathVariable Long projectId,
            @RequestParam PlanningType planningType) {
        projectService.updatePlanningType(projectId, planningType);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }
}