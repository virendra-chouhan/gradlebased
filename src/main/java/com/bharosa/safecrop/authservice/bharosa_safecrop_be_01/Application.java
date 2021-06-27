/**
 * This is APIController for AuthService for safecrop_backend_v0.1
 * all rights are reserved by Baelworks Innovations Pvt. Ltd. from @2021
 */

package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication

public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
