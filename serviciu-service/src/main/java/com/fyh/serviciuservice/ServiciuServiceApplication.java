package com.fyh.serviciuservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServiciuServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiciuServiceApplication.class, args);
	}

}
