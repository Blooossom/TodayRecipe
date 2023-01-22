package com.example.todayrecipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TodayRecipeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodayRecipeApplication.class, args);
	}

}
