package io.github.mariazevedo88.financialjavaapi;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

/**
 * Class that starts the application
 * 
 * @author Mariana Azevedo
 * @since 03/04/2020 
 */
@Log4j2
@SpringBootApplication
public class FinancialJavaApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FinancialJavaApiApplication.class, args);
		log.info("FinancialJavaAPI started successfully at {}", LocalDateTime.now());
	}

}
