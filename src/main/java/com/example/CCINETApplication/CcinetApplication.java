package com.example.CCINETApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CcinetApplication {

	public static void main(String[] args) {
		SpringApplication.run(CcinetApplication.class, args);
	}

}
