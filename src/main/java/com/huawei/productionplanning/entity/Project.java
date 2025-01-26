package com.huawei.productionplanning.entity;

import com.huawei.productionplanning.enums.PlanningType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "projects")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @Where(clause = "deleted = false")
    private List<Model> models;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @Where(clause = "deleted = false")
    private List<ProductionTarget> productionTargets;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private PlanningType planningType;
}