package com.fyh.specialistservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpecialistServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpecialistServiceApplication.class, args);
	}

}
