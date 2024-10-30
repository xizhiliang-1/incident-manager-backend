package com.felix.incident_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class IncidentManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IncidentManagerApplication.class, args);
	}

}
