package clan.hanma.identidad_service.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server gatewayServer = new Server();
        gatewayServer.setUrl("/");
        gatewayServer.setDescription("API Gateway");
        return new OpenAPI().servers(List.of(gatewayServer));
    }
}
