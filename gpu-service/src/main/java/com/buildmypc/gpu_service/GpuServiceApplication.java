package com.buildmypc.gpu_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GpuServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GpuServiceApplication.class, args);
	}
}