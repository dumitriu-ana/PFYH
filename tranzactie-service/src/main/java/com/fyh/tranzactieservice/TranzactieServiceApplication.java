package com.fyh.tranzactieservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TranzactieServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TranzactieServiceApplication.class, args);
	}

}
