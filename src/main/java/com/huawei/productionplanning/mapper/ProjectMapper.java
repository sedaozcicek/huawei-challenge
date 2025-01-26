package com.huawei.productionplanning.mapper;

import com.huawei.productionplanning.dto.ModelWithPartsDTO;
import com.huawei.productionplanning.dto.PartProductionDTO;
import com.huawei.productionplanning.dto.ProjectDTO;
import com.huawei.productionplanning.dto.ProjectWithModelsDTO;
import com.huawei.productionplanning.entity.Model;
import com.huawei.productionplanning.entity.ModelPart;
import com.huawei.productionplanning.entity.Project;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    public List<ProjectDTO> convertAllProjectsDTO(List<Project> allActiveProjects) {
        if (allActiveProjects == null || allActiveProjects.isEmpty()) {
            throw new IllegalArgumentException("Project list is empty or null.");
        }
        return allActiveProjects.stream()
                .map(project -> ProjectDTO.builder()
                        .id(project.getId())
                        .name(project.getName())
                        .startDate(project.getStartDate())
                        .endDate(project.getEndDate())
                        .build())
                .collect(Collectors.toList());
    }


    public ProjectWithModelsDTO convertToProjectWithModelsDTO(Project project) {
        List<ModelWithPartsDTO> modelsDTO = project.getModels().stream()
                .filter(model -> Boolean.TRUE.equals(model.getIsActive()))
                .map(this::convertToModelWithPartsDTO)
                .collect(Collectors.toList());

        return ProjectWithModelsDTO.builder()
                .projectName(project.getName())
                .models(modelsDTO)
                .build();
    }

    private ModelWithPartsDTO convertToModelWithPartsDTO(Model model) {
        List<PartProductionDTO> modelPartsDTO = model.getModelParts().stream()
                .map(this::convertPartProductionDTO)
                .collect(Collectors.toList());

        modelPartsDTO.sort(Comparator.comparing(PartProductionDTO::getRequiredQuantity).reversed());

        return ModelWithPartsDTO.builder()
                .modelName(model.getName())
                .modelParts(modelPartsDTO)
                .build();
    }

    private PartProductionDTO convertPartProductionDTO(ModelPart modelPart) {

        PartProductionDTO partProductionDTO = new PartProductionDTO();
        partProductionDTO.setName(modelPart.getPart().getName());
        partProductionDTO.setCode(modelPart.getPart().getCode());
        partProductionDTO.setRequiredQuantity(modelPart.getQuantity());
        return partProductionDTO;
    }




}
