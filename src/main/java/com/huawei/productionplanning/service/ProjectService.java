package com.huawei.productionplanning.service;

import com.huawei.productionplanning.dto.ModelDTO;
import com.huawei.productionplanning.dto.ProjectDTO;
import com.huawei.productionplanning.entity.Project;
import com.huawei.productionplanning.enums.PlanningType;

import java.util.HashMap;
import java.util.List;

public interface ProjectService {
    Project createProject(ProjectDTO projectDTO, PlanningType planningType);
    void deleteProject(Long projectId);
    List<ProjectDTO> listProjects();
    HashMap<String, HashMap<String, List<String>>> getProjectById(Long id);
    Project getProjectModelById(Long id);
    void updatePlanningType(Long projectId, PlanningType newPlanningType);
    HashMap<String, List<String>> sortByPlanningType(Project project);
}
