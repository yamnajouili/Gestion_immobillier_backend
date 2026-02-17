package com.example.gestionimmobilier.configuration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.media.Schema;

@Configuration
public class SwaggerConfig {



        @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI()
                    .components(new Components()

                            .addSchemas("file", new Schema().type("string").format("binary")))
                    .info(new Info()
                            .title("API Appartements")
                            .version("1.0"));
        }
    }

