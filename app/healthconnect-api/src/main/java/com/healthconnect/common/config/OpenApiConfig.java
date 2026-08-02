package com.healthconnect.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "HealthConnect API",
                version = "1.0.0",
                description = "Enterprise Healthcare Benefits Management Platform",
                contact = @Contact(
                        name = "DevOps Team",
                        email = "devops@healthconnect.com"
                ),
                license = @License(
                        name = "Internal Enterprise Application"
                )
        )
)
public class OpenApiConfig {
}

