package com.example.staffmanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi staffApi() {
        return GroupedOpenApi.builder()
                .group("staff-management")
                .pathsToMatch("/api/**")
                .build();
    }
}
