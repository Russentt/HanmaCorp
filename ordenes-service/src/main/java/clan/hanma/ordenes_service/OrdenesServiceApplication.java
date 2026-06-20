package clan.hanma.ordenes_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "API de Ordenes",
				version = "1.0.1",
				description = "Documentacion de API de Ordenes con todos los endpoints creados",
				contact = @Contact(
						name= "Alexander Mejias",
						email= "a.mejias@duocuc.cl"
				)
		)
)
public class OrdenesServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrdenesServiceApplication.class, args);
	}

}
