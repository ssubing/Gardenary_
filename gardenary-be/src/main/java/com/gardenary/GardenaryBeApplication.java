package com.gardenary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class GardenaryBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GardenaryBeApplication.class, args);
	}

}
