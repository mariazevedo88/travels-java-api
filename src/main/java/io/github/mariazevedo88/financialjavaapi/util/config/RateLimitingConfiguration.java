package io.github.mariazevedo88.financialjavaapi.util.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.mariazevedo88.financialjavaapi.util.interceptor.RateLimitInterceptor;

@Configuration
public class RateLimitingConfiguration implements WebMvcConfigurer {
	
	@Autowired
	private RateLimitInterceptor interceptor;
	
	@Override 
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/financial/v1/transactions/**",
        		"/financial/v1/statistics/**");
    }

}
