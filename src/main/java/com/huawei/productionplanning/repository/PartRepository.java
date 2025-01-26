package com.huawei.productionplanning.repository;

import com.huawei.productionplanning.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    @Query("SELECT p FROM Part p WHERE p.id=:id")
    Optional<Part> findActivePart(@Param("id") Long id);
}