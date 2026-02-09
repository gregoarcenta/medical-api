package com.medical.api.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components().addSecuritySchemes("bearer-key",
                                                                            new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                                                                    .scheme("bearer")
                                                                                    .bearerFormat("JWT")
                                                                                    .description(
                                                                                            "Ingrese el token JWT obtenido en el login")
        )).info(new Info().title("Medical API")
                        .version("1.0") // Es buena práctica poner la versión
                        .description("API Rest para la gestión de una clínica médica. " +
                                     "Incluye administración de médicos, pacientes, agendamiento y cancelación de citas.")
                        .contact(new Contact().name("Equipo Backend").email("backend@medical.api"))
                        .license(new License().name("Apache 2.0").url("http://medical.api/licencia")));
    }
}