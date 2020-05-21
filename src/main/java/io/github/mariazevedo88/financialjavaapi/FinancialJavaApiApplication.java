package io.github.mariazevedo88.financialjavaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Class that starts the application
 * 
 * @author Mariana Azevedo
 * @since 03/04/2020 
 *
 */
@EnableZuulProxy
@SpringBootApplication
@SpringCloudApplication
public class FinancialJavaApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FinancialJavaApiApplication.class, args);
	}

}
