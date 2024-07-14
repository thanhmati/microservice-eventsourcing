package com.ltfullstack.employeeservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "LT FullStack",
                        email = "contact@gmai.com",
                        url = "https://laptrinhfullstack.vercel.app"
                ),
                description = "Api Documentation for Employee Service",
                title = "Employee Api specification - LT FullStack",
                version = "1.0",
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                ),
                termsOfService = "https://laptrinhfullstack.vercel.app/terms"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:9002"
                ),
                @Server(
                        description = "Prod ENV",
                        url = "https://employee-service.com"
                ),
        }
)
public class OpenApiConfig {
}
