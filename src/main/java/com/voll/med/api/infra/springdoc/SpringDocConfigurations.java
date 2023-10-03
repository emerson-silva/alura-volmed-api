package com.voll.med.api.infra.springdoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfigurations {
@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                        .info(new Info()
                                .title("Voll.med API - Emerson")
                                .description("API Rest da aplicação Voll.med desenvolvida durante o curso de SpringBoot da Alura, contendo as funcionalidades de CRUD de médicos, pacientes e usuários, além de agendamento e cancelamento de consultas")
                                .contact(new Contact()
                                        .name("Contato")
                                        .email("contato@vollmed.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://voll.med/api/licenca")));
    }
}
