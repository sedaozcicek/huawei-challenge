package com.huawei.productionplanning.service.impl;

import com.huawei.productionplanning.dto.ProjectDTO;
import com.huawei.productionplanning.entity.Model;
import com.huawei.productionplanning.entity.ProductionTarget;
import com.huawei.productionplanning.entity.Project;
import com.huawei.productionplanning.enums.Months;
import com.huawei.productionplanning.enums.PlanningType;
import com.huawei.productionplanning.exception.ModelAlreadyExistException;
import com.huawei.productionplanning.exception.ResourceNotFoundException;
import com.huawei.productionplanning.mapper.ProjectMapper;
import com.huawei.productionplanning.repository.ModelRepository;
import com.huawei.productionplanning.repository.ProjectRepository;
import com.huawei.productionplanning.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ModelRepository modelRepository;
    private final ProjectMapper projectMapper;

    @Override
    @Transactional
    public Project createProject(ProjectDTO projectDTO, PlanningType planningType) {
        projectRepository.findActiveProjectByName(projectDTO.getName())
                .ifPresent(p -> { throw new ModelAlreadyExistException("A project with the given name already exists: " + projectDTO.getName());
                });
        Project project = Project.builder()
                .name(projectDTO.getName())
                .startDate(projectDTO.getStartDate())
                .endDate(projectDTO.getEndDate())
                .planningType(planningType)
                .build();

        log.info("Creating new project: {}", project.getName());
        return projectRepository.save(project);
    }


    @Override
    @Transactional
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findActiveProjectById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        projectRepository.delete(project);
        log.info("Soft deleted project: {}", projectId);
    }

    @Override
    public List<ProjectDTO> listProjects() {
        List<Project> allActiveProjects = projectRepository.findAllActive();
        if (CollectionUtils.isNotEmpty(allActiveProjects)){
            return projectMapper.convertAllProjectsDTO(allActiveProjects);
        }
        return Collections.emptyList();
    }

    @Override
    public HashMap<String, HashMap<String, List<String>>> getProjectById(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        HashMap<String, HashMap<String, List<String>>> monthlyMap = new HashMap<>();

        for (ProductionTarget productionTarget : project.getProductionTargets()) {
            if (Objects.nonNull(productionTarget)) {

                Months month = productionTarget.getMonth();
                HashMap<String, List<String>> byMonthly = sortByPlanningTypes(project, project.getPlanningType());
                monthlyMap.put(month.name(), byMonthly);
            }
        }
        return monthlyMap;
    }


    public HashMap<String, List<String>> sortByPlanningTypes(Project project, PlanningType planningType) {
        HashMap<String, List<String>> monthlyDistribution = new HashMap<>();

        List<Model> modelsByProjectIdOrderByPercentageDesc = modelRepository.findModelsByProjectIdAndPlanningType(project, planningType.name());
        List<String> modelDtos = modelNameList(modelsByProjectIdOrderByPercentageDesc);
        monthlyDistribution.put(planningType.name(), modelDtos);
        return monthlyDistribution;
    }

    private static List<String> modelNameList(List<Model> modelsByProjectIdOrderByPercentageDesc) {
        return modelsByProjectIdOrderByPercentageDesc.stream()
                .filter(Objects::nonNull)
                .map(Model::getName)
                .collect(Collectors.toList());
    }

    @Override
    public HashMap<String, List<String>> sortByPlanningType(Project project) {
        HashMap<String, List<String>> monthlyDistribution = new HashMap<>();

        List<Model> modelsByProjectIdOrderByPercentageDesc = modelRepository.findModelsByProjectIdAndPlanningTypeFixed(project);
        List<String> modelDtos = modelNameList(modelsByProjectIdOrderByPercentageDesc);
        monthlyDistribution.put(project.getPlanningType().name(), modelDtos);
        return monthlyDistribution;
    }


    @Override
    public Project getProjectModelById(Long id){
        Project project = projectRepository.findActiveProjectById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return Objects.nonNull(project) ? project : null;
    }

    @Override
    @Transactional
    public void updatePlanningType(Long modelId, PlanningType newPlanningType) {
        Project project = projectRepository.findActiveProjectById(modelId)
                .orElseThrow(() -> new ResourceNotFoundException("Model not found"));

        project.setPlanningType(newPlanningType);
        projectRepository.save(project);

        log.info("Updated planning type for project {}: {}", project.getName(), newPlanningType);
    }

}