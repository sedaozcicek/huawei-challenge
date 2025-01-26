package com.huawei.productionplanning.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Production Planning API")
                        .description("Production Planning and Distribution System API Documentation")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Seda Ozcicek")
                                .email("90seda09@gmail.com")));
    }
}