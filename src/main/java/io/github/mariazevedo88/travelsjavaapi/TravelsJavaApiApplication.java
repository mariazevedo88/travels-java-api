package io.github.mariazevedo88.travelsjavaapi;

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
public class TravelsJavaApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TravelsJavaApiApplication.class, args);
		log.info("TravelsJavaAPI started successfully at {}", LocalDateTime.now());
	}

}
