package com.hangout.hangout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableJpaAuditing
public class HangoutApplication {

	public static void main(String[] args) {
		SpringApplication.run(HangoutApplication.class, args);
	}

}
