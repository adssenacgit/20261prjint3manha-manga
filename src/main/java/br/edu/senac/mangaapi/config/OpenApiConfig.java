package br.edu.senac.mangaapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI mangaApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Manga API")
                        .description("API REST para gerenciamento de mangás, capítulos, páginas, usuários e favoritos.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Senac RJ")
                                .email("suporte@senac.br")));
    }
}
