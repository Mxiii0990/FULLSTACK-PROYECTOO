package com.buildmypc.motherboard_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients
public class MotherboardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotherboardServiceApplication.class, args);
	}

}
