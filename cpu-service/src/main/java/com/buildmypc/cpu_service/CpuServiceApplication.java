package com.buildmypc.cpu_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // Importante

@SpringBootApplication
@EnableFeignClients
public class CpuServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CpuServiceApplication.class, args);
	}
}