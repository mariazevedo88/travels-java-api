package io.github.mariazevedo88.financialjavaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Class that starts the application
 * 
 * @author Mariana Azevedo
 * @since 03/04/2020 
 *
 */
@EnableCaching
@SpringBootApplication
public class FinancialJavaApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FinancialJavaApiApplication.class, args);
//		new SpringApplicationBuilder(FinancialJavaApiApplication.class)
//        .properties("spring.config.location=ratelimiting/application-bucket4j-starter.yml")
//        .run(args);
	}

}
