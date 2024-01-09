package com.snowthon.snowman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SnowmanApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnowmanApplication.class, args);
	}

}
