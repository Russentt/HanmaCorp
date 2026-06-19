package clan.hanma.marketplace_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "API de Marketplace",
				version = "1.0.1",
				description = "Documentacion de API de Marketplace con todos los endpoints creados",
				contact = @Contact(
						name= "Ignacio Battistoni Mestre",
						email= "ig.battistoni@duocuc.cl"
				)
		)
)
public class MarketplaceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceServiceApplication.class, args);
	}

}
