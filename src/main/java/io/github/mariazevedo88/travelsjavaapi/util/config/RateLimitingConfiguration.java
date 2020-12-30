package io.github.mariazevedo88.travelsjavaapi.util.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.mariazevedo88.travelsjavaapi.util.interceptor.RateLimitInterceptor;

/**
 * Class that implements the necessary settings for the rate limiting in the API
 *  
 * @author Mariana Azevedo
 * @since 11/06/2020
 */
@Configuration
public class RateLimitingConfiguration implements WebMvcConfigurer {
	
	private RateLimitInterceptor interceptor;
	
	@Autowired
	public RateLimitingConfiguration(RateLimitInterceptor interceptor) {
		this.interceptor = interceptor;
	}
	
	/**
	 * Method that allow intercepting routes for rate limiting
	 * 
	 * @author Mariana Azevedo
	 * @since 11/06/2020
	 */
	@Override 
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/api-travels/v1/travels/**",
        		"/api-travels/v1/statistics/**");
    }

}
