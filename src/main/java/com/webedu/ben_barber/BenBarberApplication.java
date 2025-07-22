package com.webedu.ben_barber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BenBarberApplication {

	public static void main(String[] args) {
		SpringApplication.run(BenBarberApplication.class, args);
	}

}