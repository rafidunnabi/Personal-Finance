package com.personal.finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

@SpringBootApplication
public class FinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceApplication.class, args);


	}
	@Bean
	public DateFormat dateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}
}
