package com.huawei.productionplanning.repository;

import com.huawei.productionplanning.entity.ProductionTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionTargetRepository extends JpaRepository<ProductionTarget, Long> {

}
