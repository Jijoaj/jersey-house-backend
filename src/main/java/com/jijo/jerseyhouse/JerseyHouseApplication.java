package com.jijo.jerseyhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JerseyHouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(JerseyHouseApplication.class, args);
	}

}
