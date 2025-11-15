package br.com.fiap.chameleonfutureacademy.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {

    @Bean
    ModelResolver modelResolver(ObjectMapper objectMapper) {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return new ModelResolver(objectMapper);
    }

    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI().info(
                new Info()
                        .title("Chameleon Future Academy API")
                        .version("1.0.0")
                        .description("""
                                A Chameleon Future Academy é uma plataforma de desenvolvimento educacional
                                voltada para capacitar profissionais para os desafios do futuro.
                                O sistema permite que usuários explorem trilhas de aprendizagem, realizem cursos,
                                acompanhem seu progresso, obtenham badges e construam um perfil acadêmico
                                dinâmico que reflete suas habilidades e conquistas.
                                """));
    }

}
