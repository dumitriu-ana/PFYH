package com.fyh.comandaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.fyh.comandaservice.service")
public class ComandaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComandaServiceApplication.class, args);
	}

}
