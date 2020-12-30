package io.github.mariazevedo88.travelsjavaapi.util.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Class that implements a JwtToken utility methods
 * 
 * @author Mariana Azevedo
 * @since 11/10/2020
 */
@Component
public class JwtTokenUtil {
	
	/**
	 * Field to represent the claim key username
	 */
	private static final String CLAIM_KEY_USERNAME = "sub";
	
	/**
	 * Field to represent the claim key role
	 */
	private static final String CLAIM_KEY_ROLE = "role";
	
	/**
	 * Field to represent the claim key created
	 */
	private static final String CLAIM_KEY_CREATED = "created";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	/**
	 * Method that returns the username from the valid token.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param token
	 * @return String - username
	 */
	public String getUsernameFromToken(String token) {
		
		String username;
		
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		
		return username;
	}

	/**
	 * Method that returns the expiration date from the valid token.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param token
	 * @return Date - expirationDate
	 */
	public Date getExpirationDateFromToken(String token) {
		
		Date expiration;
		
		try {
			Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		
		return expiration;
	}

	/**
	 * Method that returns if the token is valid or not.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param token
	 * @return boolean
	 */
	public boolean validToken(String token) {
		return !expiredToken(token);
	}

	/**
	 * Method that returns the generated token.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param userDetails
	 * @return String - token
	 */
	public String getToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_CREATED, new Date());
		userDetails.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE, authority.getAuthority()));

		return generateToken(claims);
	}
	
	/**
	 * Method that returns the generated token.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param username
	 * @return String - token
	 */
	public String getToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, username);
		claims.put(CLAIM_KEY_CREATED, new Date());

		return generateToken(claims);
	}

	/**
	 * Method that parses the specified compact serialized JWS string based on the builder's current 
	 * configuration state and returns the resulting Claims JWS instance. 
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param token
	 * @return Claims object
	 */
	private Claims getClaimsFromToken(String token) throws AuthenticationException {
		
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		
		return claims;
	}

	/**
	 * Method that returns the expiration date of a token.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @return Date - expirationDate 
	 */
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}
	
	/**
	 * Method that returns if the token is expired or not
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param token
	 * @return boolean
	 */
	private boolean expiredToken(String token) {
		Date expirationDate = this.getExpirationDateFromToken(token);
		if (expirationDate == null) {
			return false;
		}
		return expirationDate.before(new Date());
	}
	
	/**
	 * Method that generates an valid token for the authenticated user.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param claims
	 * @return String - token
	 */
	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
}
