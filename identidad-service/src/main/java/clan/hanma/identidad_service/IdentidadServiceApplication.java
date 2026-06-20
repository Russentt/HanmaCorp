package clan.hanma.identidad_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@OpenAPIDefinition(
	info = @Info(
		title = "API de Identidad",
		version = "1.0.1",
		description = "Documentacion de API de identidad con todos los endpoints creados",
		contact = @Contact(
			name= "Ignacio Battistoni Mestre",
			email= "ig.battistoni@duocuc.cl"
		)
	)
)
public class IdentidadServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdentidadServiceApplication.class, args);
	}

}
