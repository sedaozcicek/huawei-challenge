package com.huawei.productionplanning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProductionPlanningApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductionPlanningApplication.class, args);
    }

}
