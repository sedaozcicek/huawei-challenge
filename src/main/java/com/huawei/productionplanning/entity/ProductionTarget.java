package com.huawei.productionplanning.entity;

import com.huawei.productionplanning.enums.Months;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "production_target")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductionTarget extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Months month;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private Integer productionTargetQuantity;
}
