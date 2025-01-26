package com.huawei.productionplanning.repository;

import com.huawei.productionplanning.entity.ProductionTarget;
import com.huawei.productionplanning.entity.Project;
import com.huawei.productionplanning.enums.Months;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductionTargetRepository extends JpaRepository<ProductionTarget, Long> {

    @Query("SELECT pt FROM ProductionTarget pt WHERE pt.project.id=:project_id and pt.month=:month")
    ProductionTarget getTargetQuantityByMonth(@Param("project_id") Long projectId, @Param("month") Months month);

    @Query("SELECT pt FROM ProductionTarget pt WHERE pt.month=:month")
    List<ProductionTarget> getTargetQuantityListByMonth(@Param("month") Months month);

}
