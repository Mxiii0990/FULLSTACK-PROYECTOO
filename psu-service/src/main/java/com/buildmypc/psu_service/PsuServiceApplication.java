package com.buildmypc.psu_service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients
public class PsuServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsuServiceApplication.class, args);
	}

}
