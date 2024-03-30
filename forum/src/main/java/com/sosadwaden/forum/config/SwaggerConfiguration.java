package com.sosadwaden.forum.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Value("${server.port}")
    private String port;

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .servers(
                        List.of(
                                new Server().url("http://localhost:" + port)
                        )
                )
                .info(
                        new Info()
                                .title("Forum test task")
                                .description("Test task in the form of a forum for Rosatom.")
                );
    }
}
