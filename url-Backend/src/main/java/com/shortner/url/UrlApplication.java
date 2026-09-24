package com.shortner.url;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCaching
public class UrlApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlApplication.class, args);
	}

}
