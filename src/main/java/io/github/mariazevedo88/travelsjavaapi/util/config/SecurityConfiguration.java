package io.github.mariazevedo88.travelsjavaapi.util.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.github.mariazevedo88.travelsjavaapi.filters.JwtAuthenticationEntryPointFilter;
import io.github.mariazevedo88.travelsjavaapi.filters.JwtAuthenticationTokenFilter;

/**
 * Class that implements the Travels Java API Spring Security configurations.
 * 
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private JwtAuthenticationEntryPointFilter unauthorizedHandler;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Value("${spring.profiles.active}")
	private String activeProfile;

	/**
	 * Method to configure the type of authentication in the API
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 * 
	 * @param authenticationManagerBuilder
	 * @throws Exception
	 */
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}

	/**
	 * @see WebSecurityConfigurerAdapter#authenticationManagerBean()
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 */
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * Method to create an BCrypt password encoder
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 * 
	 * @return PasswordEncoder object
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Method to create an Authentication Token Filter Bean
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 * 
	 * @return JwtAuthenticationTokenFilter object
	 */
	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilterBean() {
		return new JwtAuthenticationTokenFilter();
	}
	
	/**
	 * @see WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.WebSecurity)
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (activeProfile.equals("test")) {
            http.csrf().disable().authorizeRequests().anyRequest().permitAll();
        } else {
        	http.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
        	.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
        	.antMatchers("/api-travels/v1/auth/**", "/api-travels/v1/users/**", "/configuration/security", "/webjars/**", 
        			"/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/manage/**")
    		.permitAll().anyRequest().authenticated();
    		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    		http.headers().cacheControl();
        }
	}
}