package com.huawei.productionplanning.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(name = "models")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Model extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    @Where(clause = "deleted = false")
    private List<ModelPart> modelParts;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    @Where(clause = "deleted = false")
    private List<ModelDistribution> distributions;
}