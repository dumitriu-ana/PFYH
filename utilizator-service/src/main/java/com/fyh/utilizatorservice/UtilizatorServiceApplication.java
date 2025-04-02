package com.fyh.utilizatorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UtilizatorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtilizatorServiceApplication.class, args);
	}

}
