package com.huawei.productionplanning.repository;

import com.huawei.productionplanning.entity.Model;
import com.huawei.productionplanning.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    @Query("SELECT m FROM Model m WHERE m.project.id = :projectId")
    List<Model> findAllActiveByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT m FROM Model m WHERE m.id = :modelId")
    Optional<Model> findActiveByModelId(@Param("modelId") Long modelId);

    @Query("""
    SELECT m FROM Model m JOIN m.distributions md WHERE m.project = :project AND m.isActive = true AND (
        (:planningType = 'MONTHLY' AND md.month IS NOT NULL) OR (:planningType = 'WEEKLY' AND md.weekOfYear IS NOT NULL)) ORDER BY md.percentage DESC""")
    List<Model> findModelsByProjectIdAndPlanningType(
            @Param("project") Project project,
            @Param("planningType") String planningType
    );

    @Query("""
    SELECT m FROM Model m JOIN m.distributions md WHERE m.project = :project AND m.isActive = true AND md.planningType = 'FIXED' ORDER BY md.percentage DESC""")
    List<Model> findModelsByProjectIdAndPlanningTypeFixed(
            @Param("project") Project project
    );
}