package com.economic.app.economic_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class EconomicAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EconomicAppApplication.class, args);
	}
	
	@Bean
	public OpenAPI economicAppOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Economic App API")
						.description("API para gerenciamento financeiro pessoal")
						.version("v1.0")
						.contact(new Contact()
								.name("Economic App Team")
								.email("contato@economicapp.com"))
						.license(new License()
								.name("MIT License")
								.url("https://opensource.org/licenses/MIT")));
	}
}
