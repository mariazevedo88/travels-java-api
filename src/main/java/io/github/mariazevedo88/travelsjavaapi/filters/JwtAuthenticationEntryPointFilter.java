package io.github.mariazevedo88.travelsjavaapi.filters;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Class that implements AuthenticationEntryPoint interface to modify the headers on the 
 * <code>ServletResponse</code> as necessary to commence the authentication process and 
 * customize unauthorized access responses.
 * 
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
@Component
public class JwtAuthenticationEntryPointFilter implements AuthenticationEntryPoint {

	/**
	 * Method that implements a customization to unauthorized invalid access responses.
	 * 
	 * @see AuthenticationEntryPoint#commence(HttpServletRequest, HttpServletResponse, AuthenticationException)
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException 
			authException) throws IOException, ServletException {
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				"Access denied. You must be authenticated on the system to access the requested URL.");
	}

}
