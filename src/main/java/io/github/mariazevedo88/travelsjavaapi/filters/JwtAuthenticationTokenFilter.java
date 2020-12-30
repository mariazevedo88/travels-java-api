package io.github.mariazevedo88.travelsjavaapi.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.mariazevedo88.travelsjavaapi.util.security.JwtTokenUtil;

/**
 * Class implementation of a JwtAuthentication filter base class that aims to guarantee a single execution 
 * per request dispatch, on any servlet container. If we had valid token, the request is done. 
 * 
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
	
	private static final String AUTH_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	@Autowired
    private UserDetailsService userDetailsService;

	@Autowired
    private JwtTokenUtil jwtTokenUtil;

	/**
	 * Method that implements doFilter, but guaranteed to be just invoked once per request within 
	 * a single request thread.
	 * 
	 * @see OncePerRequestFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
			throws ServletException, IOException {
		
		String token = request.getHeader(AUTH_HEADER);
        if (token != null && token.startsWith(BEARER_PREFIX)) {
        	token = token.substring(7);
        }
        String username = jwtTokenUtil.getUsernameFromToken(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validToken(token)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, 
                		userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
	}
}

