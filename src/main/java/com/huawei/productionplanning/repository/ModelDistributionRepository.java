package com.huawei.productionplanning.repository;

import com.huawei.productionplanning.entity.Model;
import com.huawei.productionplanning.entity.ModelDistribution;
import com.huawei.productionplanning.enums.Months;
import com.huawei.productionplanning.enums.PlanningType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelDistributionRepository extends JpaRepository<ModelDistribution, Long> {
    @Query("""
    SELECT md FROM ModelDistribution md join md.model m JOIN m.project p WHERE p.id = :projectId and p.planningType = :planningType AND md.month >= :startDate AND md.month <= :endDate ORDER BY md.month ASC""")
    List<ModelDistribution> findModelDistributionsByDateRange(
            @Param("projectId") Long projectId,
            @Param("startDate") Months startDate,
            @Param("endDate") Months endDate,
            @Param("planningType") PlanningType planningType
    );

    @Query("SELECT m FROM Model m JOIN m.distributions md WHERE m.project.id = :projectId and md.planningType= :planningType and md.month IS NOT NULL and m.isActive=true ORDER BY md.percentage DESC")
    List<Model> findModelsByProjectIdOrderByPercentageDesc(@Param("projectId") Long projectId, @Param("planningType") PlanningType planningType);


    @Query("SELECT md FROM ModelDistribution md WHERE md.id = :distributionId")
    Optional<ModelDistribution> findDistribution(@Param("distributionId") Long distributionId);

    @Query("SELECT md FROM ModelDistribution md WHERE md.model.id = :modelId AND md.month= :month")
    ModelDistribution findModelDistributionByModelAndMonth(@Param("modelId") Long modelId, @Param("month") Months month );

    @Query("SELECT md FROM ModelDistribution md WHERE md.model.id = :modelId AND md.weekOfYear= :weekOfYear")
    ModelDistribution findModelDistributionByModelAndWeekOfYear(@Param("modelId") Long modelId, @Param("weekOfYear") Integer weekOfYear);

    @Query("SELECT md FROM ModelDistribution md WHERE md.model.id = :modelId AND md.planningType= :planningType")
    ModelDistribution findModelDistributionByModelAndPlanningType(@Param("modelId") Long modelId, @Param("planningType") PlanningType planningType);

}