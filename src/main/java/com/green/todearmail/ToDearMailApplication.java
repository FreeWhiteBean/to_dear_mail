package com.green.todearmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ToDearMailApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDearMailApplication.class, args);
	}

}
