package com.buildmypc.builder_service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients
public class BuilderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuilderServiceApplication.class, args);
	}

}
