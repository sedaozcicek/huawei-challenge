package com.huawei.productionplanning.repository;

import com.huawei.productionplanning.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p")
    List<Project> findAllActive();

    @Query("SELECT p FROM Project p WHERE p.id = :id")
    Optional<Project> findActiveProjectById(@Param("id") Long id);

    @Query("SELECT p FROM Project p WHERE p.name = :name")
    Optional<Project> findActiveProjectByName(@Param("name") String name);

}