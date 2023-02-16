package com.tfm.aseguradora.backend.tfm.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.tfm.aseguradora.backend.tfm.users.dataaccess.entity"})
@EnableJpaRepositories(basePackages = "com.tfm.aseguradora.backend.tfm.users.dataaccess.repository")
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
