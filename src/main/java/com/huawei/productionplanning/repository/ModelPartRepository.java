package com.huawei.productionplanning.repository;

import com.huawei.productionplanning.entity.ModelPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelPartRepository extends JpaRepository<ModelPart, Long> {
    @Query("SELECT mp FROM ModelPart mp WHERE mp.part.id= :partId AND mp.model.id = :modelId")
    Optional<ModelPart> findModelPartByPartAndModel(@Param("partId") Long partId,@Param("modelId") Long modelId);
}