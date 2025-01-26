package com.huawei.productionplanning.entity;

import com.huawei.productionplanning.enums.Months;
import com.huawei.productionplanning.enums.PlanningType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "model_distributions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelDistribution extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    private Months month;
    private Integer weekOfYear;
    private Double percentage;
    @Enumerated(EnumType.STRING)
    private PlanningType planningType;
}